# setup-sdkman.ps1 - SDKMAN setup script for Hacker's Keyboard development (Windows)
# Installs and configures Java (GraalVM CE), Kotlin, and Gradle via SDKMAN on WSL or Git Bash

$ErrorActionPreference = "Stop"

# Required versions
$JAVA_VERSION = "21.0.2-graalce"
$KOTLIN_VERSION = "2.3.10"
$GRADLE_VERSION = "9.3.1"

function Write-Info { param($msg) Write-Host "[INFO] $msg" -ForegroundColor Green }
function Write-Warn { param($msg) Write-Host "[WARN] $msg" -ForegroundColor Yellow }
function Write-Err { param($msg) Write-Host "[ERROR] $msg" -ForegroundColor Red }

# Check if running in WSL or Git Bash
function Test-UnixLikeEnvironment {
    if ($env:WSL_DISTRO_NAME) { return $true }
    if ($env:MSYSTEM) { return $true }  # Git Bash
    return $false
}

# Configure Gradle proxy settings for Windows
function Set-GradleProxy {
    $gradleDir = "$env:USERPROFILE\.gradle"
    $gradleProps = "$gradleDir\gradle.properties"

    if (-not (Test-Path $gradleDir)) {
        New-Item -ItemType Directory -Path $gradleDir -Force | Out-Null
    }

    $proxyConfig = @()

    # Check for proxy environment variables
    if ($env:HTTP_PROXY) {
        $uri = [System.Uri]$env:HTTP_PROXY
        Write-Info "Configuring HTTP proxy: $($uri.Host):$($uri.Port)"
        $proxyConfig += "systemProp.http.proxyHost=$($uri.Host)"
        $proxyConfig += "systemProp.http.proxyPort=$($uri.Port)"
        if ($uri.UserInfo) {
            $creds = $uri.UserInfo.Split(':')
            $proxyConfig += "systemProp.http.proxyUser=$($creds[0])"
            if ($creds.Length -gt 1) {
                $proxyConfig += "systemProp.http.proxyPassword=$($creds[1])"
            }
        }
    }

    if ($env:HTTPS_PROXY) {
        $uri = [System.Uri]$env:HTTPS_PROXY
        Write-Info "Configuring HTTPS proxy: $($uri.Host):$($uri.Port)"
        $proxyConfig += "systemProp.https.proxyHost=$($uri.Host)"
        $proxyConfig += "systemProp.https.proxyPort=$($uri.Port)"
        if ($uri.UserInfo) {
            $creds = $uri.UserInfo.Split(':')
            $proxyConfig += "systemProp.https.proxyUser=$($creds[0])"
            if ($creds.Length -gt 1) {
                $proxyConfig += "systemProp.https.proxyPassword=$($creds[1])"
            }
        }
    }

    if ($env:NO_PROXY) {
        $nonProxy = $env:NO_PROXY -replace ',', '|'
        $proxyConfig += "systemProp.http.nonProxyHosts=$nonProxy"
        $proxyConfig += "systemProp.https.nonProxyHosts=$nonProxy"
    }

    if ($proxyConfig.Count -gt 0) {
        Write-Info "Writing proxy configuration to $gradleProps"
        $existingContent = ""
        if (Test-Path $gradleProps) {
            $existingContent = Get-Content $gradleProps -Raw
        }

        $newContent = $existingContent + "`n# Proxy settings (auto-configured)`n" + ($proxyConfig -join "`n")
        Set-Content -Path $gradleProps -Value $newContent
    }
}

# Main for Windows native (uses manual downloads or Chocolatey)
function Install-WindowsNative {
    Write-Info "=== Hacker's Keyboard Development Environment Setup (Windows) ==="
    Write-Info ""

    # Check for Chocolatey
    $chocoAvailable = Get-Command choco -ErrorAction SilentlyContinue

    if ($chocoAvailable) {
        Write-Info "Using Chocolatey for installation..."

        Write-Info "Installing GraalVM CE 21..."
        choco install graalvm-java21 -y

        Write-Info "Installing Kotlin..."
        choco install kotlin -y

        Write-Info "Installing Gradle..."
        choco install gradle -y
    }
    else {
        Write-Warn "Chocolatey not found. Please install tools manually:"
        Write-Info "  - GraalVM CE 21: https://www.graalvm.org/downloads/"
        Write-Info "  - Kotlin: https://kotlinlang.org/docs/command-line.html"
        Write-Info "  - Gradle 9.3.1: https://gradle.org/releases/"
        Write-Info ""
        Write-Info "Or install Chocolatey first:"
        Write-Info '  Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString("https://community.chocolatey.org/install.ps1"))'
    }

    Write-Info ""
    Write-Info "=== Configuring Gradle Proxy ==="
    Set-GradleProxy

    Write-Info ""
    Write-Info "=== Setup Complete ==="
}

# Main for WSL/Git Bash (uses bash script)
function Install-UnixLike {
    Write-Info "Detected WSL/Git Bash environment"
    Write-Info "Running bash setup script..."

    $scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
    $bashScript = Join-Path $scriptPath "setup-sdkman.sh"

    if (Test-Path $bashScript) {
        bash $bashScript
    }
    else {
        Write-Err "Bash script not found at $bashScript"
        Write-Info "Please run setup-sdkman.sh directly"
    }
}

# Entry point
if (Test-UnixLikeEnvironment) {
    Install-UnixLike
}
else {
    Install-WindowsNative
}
