#!/usr/bin/env python3
"""Local proxy relay that adds Proxy-Authorization to upstream proxy.

Listens on localhost:18080 (no auth required).
Forwards CONNECT/HTTP requests to upstream proxy with Basic auth header.
This lets JVM/Gradle use a no-auth proxy that transparently handles the
upstream Bearer/Basic proxy authentication.
"""
import base64
import os
import select
import socket
import sys
import threading

LISTEN_PORT = int(os.environ.get("RELAY_PORT", "18080"))
UPSTREAM_PROXY = os.environ.get("HTTPS_PROXY") or os.environ.get("HTTP_PROXY") or os.environ.get("https_proxy") or os.environ.get("http_proxy")

def parse_proxy_url(url):
    # http://user:pass@host:port
    url = url.replace("http://", "")
    if "@" in url:
        creds, hostport = url.rsplit("@", 1)
        user, password = creds.split(":", 1)
    else:
        user, password = None, None
        hostport = url
    host, port = hostport.split(":")
    return host, int(port), user, password

def make_auth_header(user, password):
    creds = f"{user}:{password}"
    b64 = base64.b64encode(creds.encode()).decode()
    return f"Proxy-Authorization: Basic {b64}\r\n"

def relay(src, dst):
    try:
        while True:
            data = src.recv(65536)
            if not data:
                break
            dst.sendall(data)
    except Exception:
        pass
    finally:
        try:
            dst.shutdown(socket.SHUT_WR)
        except Exception:
            pass

def handle_client(client_sock, upstream_host, upstream_port, auth_header):
    try:
        # Read the initial request from client
        data = b""
        while b"\r\n\r\n" not in data:
            chunk = client_sock.recv(4096)
            if not chunk:
                client_sock.close()
                return
            data += chunk

        header_end = data.index(b"\r\n\r\n")
        headers = data[:header_end].decode("utf-8", errors="replace")
        rest = data[header_end + 4:]

        # Connect to upstream proxy
        upstream = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        upstream.settimeout(30)
        upstream.connect((upstream_host, upstream_port))

        # Inject auth header into the request
        lines = headers.split("\r\n")
        request_line = lines[0]
        other_headers = [l for l in lines[1:] if not l.lower().startswith("proxy-authorization:")]

        new_headers = request_line + "\r\n" + auth_header
        for h in other_headers:
            if h:
                new_headers += h + "\r\n"
        new_headers += "\r\n"

        upstream.sendall(new_headers.encode() + rest)

        if request_line.upper().startswith("CONNECT"):
            # Read upstream response
            resp = b""
            while b"\r\n\r\n" not in resp:
                chunk = upstream.recv(4096)
                if not chunk:
                    break
                resp += chunk

            # Forward response to client
            client_sock.sendall(resp)

            # Check if tunnel established
            resp_line = resp.split(b"\r\n")[0].decode()
            if " 200 " in resp_line:
                # Bidirectional relay
                t1 = threading.Thread(target=relay, args=(client_sock, upstream), daemon=True)
                t2 = threading.Thread(target=relay, args=(upstream, client_sock), daemon=True)
                t1.start()
                t2.start()
                t1.join(timeout=300)
                t2.join(timeout=300)
            else:
                sys.stderr.write(f"[proxy-relay] Upstream CONNECT failed: {resp_line}\n")
        else:
            # Non-CONNECT: relay response back
            t1 = threading.Thread(target=relay, args=(upstream, client_sock), daemon=True)
            t1.start()
            t1.join(timeout=60)

    except Exception as e:
        sys.stderr.write(f"[proxy-relay] Error: {e}\n")
    finally:
        try:
            client_sock.close()
        except Exception:
            pass
        try:
            upstream.close()
        except Exception:
            pass

def main():
    if not UPSTREAM_PROXY:
        print("ERROR: HTTPS_PROXY or HTTP_PROXY not set", file=sys.stderr)
        sys.exit(1)

    upstream_host, upstream_port, user, password = parse_proxy_url(UPSTREAM_PROXY)
    auth_header = make_auth_header(user, password) if user else ""

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind(("127.0.0.1", LISTEN_PORT))
    server.listen(50)

    print(f"[proxy-relay] Listening on 127.0.0.1:{LISTEN_PORT} -> {upstream_host}:{upstream_port}", flush=True)

    while True:
        client_sock, addr = server.accept()
        t = threading.Thread(target=handle_client, args=(client_sock, upstream_host, upstream_port, auth_header), daemon=True)
        t.start()

if __name__ == "__main__":
    main()
