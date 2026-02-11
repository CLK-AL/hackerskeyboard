#!/bin/bash
# setup-sdkman.sh - SDKMAN setup script for Hacker's Keyboard development
# Installs and configures Java (GraalVM CE), Kotlin, and Gradle

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo_info() { echo -e "${GREEN}[INFO]${NC} $1"; }
echo_warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
echo_error() { echo -e "${RED}[ERROR]${NC} $1"; }

# Required versions
JAVA_VERSION="21.0.2-graalce"
KOTLIN_VERSION="2.3.10"
GRADLE_VERSION="9.3.1"

# Check if SDKMAN is installed
install_sdkman() {
    if [ -f "$HOME/.sdkman/bin/sdkman-init.sh" ]; then
        echo_info "SDKMAN already installed"
        return 0
    fi

    echo_info "Installing SDKMAN..."
    curl -s "https://get.sdkman.io" | bash

    if [ $? -ne 0 ]; then
        echo_error "Failed to install SDKMAN"
        exit 1
    fi
}

# Source SDKMAN
source_sdkman() {
    export SDKMAN_DIR="$HOME/.sdkman"
    if [ -f "$SDKMAN_DIR/bin/sdkman-init.sh" ]; then
        source "$SDKMAN_DIR/bin/sdkman-init.sh"
    else
        echo_error "SDKMAN not found at $SDKMAN_DIR"
        exit 1
    fi
}

# Install a candidate if not present
install_candidate() {
    local candidate=$1
    local version=$2

    if sdk list "$candidate" 2>/dev/null | grep -q "$version.*installed"; then
        echo_info "$candidate $version already installed"
    else
        echo_info "Installing $candidate $version..."
        sdk install "$candidate" "$version" < /dev/null
    fi

    # Set as default
    sdk default "$candidate" "$version" < /dev/null
}

# Configure Gradle proxy settings
configure_gradle_proxy() {
    local gradle_props="$HOME/.gradle/gradle.properties"
    mkdir -p "$HOME/.gradle"

    # Check for proxy environment variables
    if [ -n "$HTTP_PROXY" ] || [ -n "$http_proxy" ]; then
        local proxy="${HTTP_PROXY:-$http_proxy}"
        local proxy_host=$(echo "$proxy" | sed -e 's|https\?://||' -e 's|:.*||')
        local proxy_port=$(echo "$proxy" | sed -e 's|.*:||' -e 's|/||')

        echo_info "Configuring HTTP proxy: $proxy_host:$proxy_port"

        cat >> "$gradle_props" << EOF

# HTTP Proxy settings (auto-configured)
systemProp.http.proxyHost=$proxy_host
systemProp.http.proxyPort=$proxy_port
EOF
    fi

    if [ -n "$HTTPS_PROXY" ] || [ -n "$https_proxy" ]; then
        local proxy="${HTTPS_PROXY:-$https_proxy}"
        local proxy_host=$(echo "$proxy" | sed -e 's|https\?://||' -e 's|:.*||')
        local proxy_port=$(echo "$proxy" | sed -e 's|.*:||' -e 's|/||')

        echo_info "Configuring HTTPS proxy: $proxy_host:$proxy_port"

        cat >> "$gradle_props" << EOF

# HTTPS Proxy settings (auto-configured)
systemProp.https.proxyHost=$proxy_host
systemProp.https.proxyPort=$proxy_port
EOF
    fi

    if [ -n "$NO_PROXY" ] || [ -n "$no_proxy" ]; then
        local no_proxy="${NO_PROXY:-$no_proxy}"
        echo_info "Configuring proxy exclusions: $no_proxy"

        cat >> "$gradle_props" << EOF
systemProp.http.nonProxyHosts=$no_proxy
systemProp.https.nonProxyHosts=$no_proxy
EOF
    fi
}

# Main execution
main() {
    echo_info "=== Hacker's Keyboard Development Environment Setup ==="
    echo ""

    # Install SDKMAN
    install_sdkman

    # Source SDKMAN
    source_sdkman

    echo ""
    echo_info "=== Installing Development Tools ==="
    echo ""

    # Install Java (GraalVM CE)
    install_candidate "java" "$JAVA_VERSION"

    # Install Kotlin
    install_candidate "kotlin" "$KOTLIN_VERSION"

    # Install Gradle
    install_candidate "gradle" "$GRADLE_VERSION"

    echo ""
    echo_info "=== Configuring Gradle ==="
    configure_gradle_proxy

    echo ""
    echo_info "=== Verifying Installation ==="
    echo ""

    echo_info "Java version:"
    java -version
    echo ""

    echo_info "Kotlin version:"
    kotlin -version
    echo ""

    echo_info "Gradle version:"
    gradle -version | head -5
    echo ""

    echo_info "=== Setup Complete ==="
    echo ""
    echo_info "To use in a new shell, run:"
    echo "  source ~/.sdkman/bin/sdkman-init.sh"
    echo ""
    echo_info "To build the project:"
    echo "  ./gradlew assembleDebug"
}

# Run main
main "$@"
