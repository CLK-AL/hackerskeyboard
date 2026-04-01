#!/bin/bash
# Build script for Hacker's Keyboard
# Handles proxy configuration automatically

LOG_FILE="build.log"

# Ensure proxy relay is running
if ! pgrep -f proxy-relay.py > /dev/null; then
    echo "Starting proxy relay..."
    python3 proxy-relay.py &
    sleep 2
fi

# Clear environment proxy variables that interfere with local relay
unset HTTP_PROXY HTTPS_PROXY http_proxy https_proxy
unset NO_PROXY no_proxy
unset GLOBAL_AGENT_HTTP_PROXY GLOBAL_AGENT_HTTPS_PROXY GLOBAL_AGENT_NO_PROXY
unset JAVA_TOOL_OPTIONS

# Set Java proxy options for local relay
export _JAVA_OPTIONS="-Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=18080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=18080"

# Set Android SDK
export ANDROID_HOME=/opt/android-sdk

echo "Building... output to $LOG_FILE"

# Run Gradle build and log output
./gradlew "${@:-assembleDebug}" --no-daemon 2>&1 | tee "$LOG_FILE"

# Show error summary
echo ""
echo "=== Error Summary ==="
grep "^e:" "$LOG_FILE" | wc -l | xargs -I{} echo "{} errors found"
grep "^e:" "$LOG_FILE" | head -20
