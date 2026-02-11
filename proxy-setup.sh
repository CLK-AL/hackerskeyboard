#!/bin/bash
# Proxy Setup Helper Script
# Source this script to configure proxy environment variables for Gradle builds
#
# Usage:
#   source proxy-setup.sh [proxy-url]
#   source proxy-setup.sh http://proxy.example.com:8080
#   source proxy-setup.sh http://user:pass@proxy.example.com:8080
#
# Or set the proxy URL in the environment and source without arguments:
#   export PROXY_URL=http://proxy.example.com:8080
#   source proxy-setup.sh

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
    set_proxy "$1"
fi
