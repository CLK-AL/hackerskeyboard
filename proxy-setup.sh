#!/bin/bash
# Proxy Setup Helper Script
# Source this script to configure proxy environment variables for Gradle builds
#
# Usage:
#   source proxy-setup.sh [proxy-url]
#   source proxy-setup.sh http://proxy.example.com:8080
#   source proxy-setup.sh http://user:pass@proxy.example.com:8080
#
# With relay mode (for proxies requiring authentication):
#   source proxy-setup.sh --relay http://user:pass@proxy.example.com:8080
#
# Or set the proxy URL in the environment and source without arguments:
#   export PROXY_URL=http://proxy.example.com:8080
#   source proxy-setup.sh

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RELAY_PORT="${RELAY_PORT:-18080}"

start_relay() {
    local proxy_url="$1"

    if [ -z "$proxy_url" ]; then
        echo "Error: Proxy URL required for relay mode"
        return 1
    fi

    # Check if relay is already running
    if pgrep -f "proxy-relay.py" > /dev/null 2>&1; then
        echo "Proxy relay already running"
    else
        echo "Starting proxy relay on port $RELAY_PORT..."
        export HTTP_PROXY="$proxy_url"
        export HTTPS_PROXY="$proxy_url"
        python3 "$SCRIPT_DIR/proxy-relay.py" &
        RELAY_PID=$!
        sleep 1
        if kill -0 $RELAY_PID 2>/dev/null; then
            echo "Proxy relay started (PID: $RELAY_PID)"
        else
            echo "Failed to start proxy relay"
            return 1
        fi
    fi

    # Configure Gradle to use the local relay (no auth needed)
    export HTTP_PROXY="http://127.0.0.1:$RELAY_PORT"
    export http_proxy="http://127.0.0.1:$RELAY_PORT"
    export HTTPS_PROXY="http://127.0.0.1:$RELAY_PORT"
    export https_proxy="http://127.0.0.1:$RELAY_PORT"
    export NO_PROXY="localhost,::1,*.local"
    export no_proxy="$NO_PROXY"

    echo ""
    echo "Gradle proxy configured to use local relay:"
    echo "  HTTP_PROXY=$HTTP_PROXY"
    echo "  HTTPS_PROXY=$HTTPS_PROXY"
    echo "  Relay -> upstream proxy with authentication"
}

stop_relay() {
    if pkill -f "proxy-relay.py" 2>/dev/null; then
        echo "Proxy relay stopped"
    else
        echo "No proxy relay running"
    fi
}

set_proxy() {
    local proxy_url="${1:-$PROXY_URL}"

    if [ -z "$proxy_url" ]; then
        echo "Proxy Setup Helper"
        echo "=================="
        echo ""
        echo "Usage:"
        echo "  source proxy-setup.sh <proxy-url>"
        echo "  source proxy-setup.sh http://proxy.example.com:8080"
        echo "  source proxy-setup.sh http://user:pass@proxy.example.com:8080"
        echo ""
        echo "Relay mode (for authenticated proxies that don't work with JVM):"
        echo "  source proxy-setup.sh --relay http://user:pass@proxy:8080"
        echo "  source proxy-setup.sh --stop-relay"
        echo ""
        echo "To clear proxy settings:"
        echo "  source proxy-setup.sh --clear"
        echo ""
        echo "Current proxy settings:"
        echo "  HTTP_PROXY:  ${HTTP_PROXY:-<not set>}"
        echo "  HTTPS_PROXY: ${HTTPS_PROXY:-<not set>}"
        echo "  NO_PROXY:    ${NO_PROXY:-<not set>}"
        return 0
    fi

    if [ "$proxy_url" = "--clear" ] || [ "$proxy_url" = "-c" ]; then
        unset HTTP_PROXY http_proxy
        unset HTTPS_PROXY https_proxy
        unset NO_PROXY no_proxy
        echo "Proxy settings cleared."
        return 0
    fi

    if [ "$proxy_url" = "--stop-relay" ]; then
        stop_relay
        return 0
    fi

    if [ "$proxy_url" = "--relay" ]; then
        start_relay "$2"
        return $?
    fi

    # Set both upper and lowercase variants for maximum compatibility
    export HTTP_PROXY="$proxy_url"
    export http_proxy="$proxy_url"
    export HTTPS_PROXY="$proxy_url"
    export https_proxy="$proxy_url"

    # Set common no-proxy hosts
    export NO_PROXY="localhost,127.0.0.1,::1,*.local"
    export no_proxy="$NO_PROXY"

    echo "Proxy configured:"
    echo "  HTTP_PROXY=$HTTP_PROXY"
    echo "  HTTPS_PROXY=$HTTPS_PROXY"
    echo "  NO_PROXY=$NO_PROXY"
    echo ""
    echo "Gradle will auto-detect these settings via settings.gradle and init.gradle"
}

# If script is sourced with arguments, configure proxy immediately
if [ -n "$1" ]; then
    set_proxy "$@"
fi
