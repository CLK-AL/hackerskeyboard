# Proxy Setup Helper Script for PowerShell
# Dot-source this script to configure proxy environment variables for Gradle builds
#
# Usage:
#   . .\proxy-setup.ps1 [proxy-url]
#   . .\proxy-setup.ps1 http://proxy.example.com:8080
#   . .\proxy-setup.ps1 http://user:pass@proxy.example.com:8080
#
# With relay mode (for proxies requiring authentication):
#   . .\proxy-setup.ps1 -Relay http://user:pass@proxy.example.com:8080
#
# Or set the proxy URL first:
#   $env:PROXY_URL = "http://proxy.example.com:8080"
#   . .\proxy-setup.ps1

param(
    [Parameter(Position=0)]
    [string]$ProxyUrl,
    [switch]$Relay,
    [switch]$StopRelay,
    [switch]$Clear
)

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$RelayPort = if ($env:RELAY_PORT) { $env:RELAY_PORT } else { "18080" }

function Start-ProxyRelay {
    param([string]$UpstreamProxy)

    if ([string]::IsNullOrEmpty($UpstreamProxy)) {
        Write-Host "Error: Proxy URL required for relay mode" -ForegroundColor Red
        return $false
    }

    # Check if relay is already running
    $existing = Get-Process -Name "python*" -ErrorAction SilentlyContinue |
                Where-Object { $_.CommandLine -like "*proxy-relay.py*" }

    if ($existing) {
        Write-Host "Proxy relay already running"
    } else {
        Write-Host "Starting proxy relay on port $RelayPort..."
        $env:HTTP_PROXY = $UpstreamProxy
        $env:HTTPS_PROXY = $UpstreamProxy

        $relayScript = Join-Path $ScriptDir "proxy-relay.py"
        $process = Start-Process -FilePath "python3" -ArgumentList $relayScript -PassThru -NoNewWindow
        Start-Sleep -Seconds 1

        if ($process.HasExited) {
            Write-Host "Failed to start proxy relay" -ForegroundColor Red
            return $false
        }
        Write-Host "Proxy relay started (PID: $($process.Id))" -ForegroundColor Green
    }

    # Configure Gradle to use the local relay (no auth needed)
    $env:HTTP_PROXY = "http://127.0.0.1:$RelayPort"
    $env:http_proxy = "http://127.0.0.1:$RelayPort"
    $env:HTTPS_PROXY = "http://127.0.0.1:$RelayPort"
    $env:https_proxy = "http://127.0.0.1:$RelayPort"
    $env:NO_PROXY = "localhost,::1,*.local"
    $env:no_proxy = $env:NO_PROXY

    Write-Host ""
    Write-Host "Gradle proxy configured to use local relay:" -ForegroundColor Green
    Write-Host "  HTTP_PROXY=$env:HTTP_PROXY"
    Write-Host "  HTTPS_PROXY=$env:HTTPS_PROXY"
    Write-Host "  Relay -> upstream proxy with authentication"
    return $true
}

function Stop-ProxyRelay {
    $processes = Get-Process -Name "python*" -ErrorAction SilentlyContinue |
                 Where-Object { $_.CommandLine -like "*proxy-relay.py*" }

    if ($processes) {
        $processes | Stop-Process -Force
        Write-Host "Proxy relay stopped" -ForegroundColor Green
    } else {
        Write-Host "No proxy relay running"
    }
}

function Set-ProxyConfig {
    param([string]$Url)

    if ([string]::IsNullOrEmpty($Url)) {
        $Url = $env:PROXY_URL
    }

    if ([string]::IsNullOrEmpty($Url)) {
        Write-Host "Proxy Setup Helper" -ForegroundColor Cyan
        Write-Host "==================" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Usage:"
        Write-Host "  . .\proxy-setup.ps1 <proxy-url>"
        Write-Host "  . .\proxy-setup.ps1 http://proxy.example.com:8080"
        Write-Host "  . .\proxy-setup.ps1 http://user:pass@proxy.example.com:8080"
        Write-Host ""
        Write-Host "Relay mode (for authenticated proxies that don't work with JVM):"
        Write-Host "  . .\proxy-setup.ps1 -Relay http://user:pass@proxy:8080"
        Write-Host "  . .\proxy-setup.ps1 -StopRelay"
        Write-Host ""
        Write-Host "To clear proxy settings:"
        Write-Host "  . .\proxy-setup.ps1 -Clear"
        Write-Host ""
        Write-Host "Current proxy settings:"
        Write-Host "  HTTP_PROXY:  $($env:HTTP_PROXY ?? '<not set>')"
        Write-Host "  HTTPS_PROXY: $($env:HTTPS_PROXY ?? '<not set>')"
        Write-Host "  NO_PROXY:    $($env:NO_PROXY ?? '<not set>')"
        return
    }

    # Set both upper and lowercase variants for maximum compatibility
    $env:HTTP_PROXY = $Url
    $env:http_proxy = $Url
    $env:HTTPS_PROXY = $Url
    $env:https_proxy = $Url

    # Set common no-proxy hosts
    $noProxy = "localhost,127.0.0.1,::1,*.local"
    $env:NO_PROXY = $noProxy
    $env:no_proxy = $noProxy

    Write-Host "Proxy configured:" -ForegroundColor Green
    Write-Host "  HTTP_PROXY=$env:HTTP_PROXY"
    Write-Host "  HTTPS_PROXY=$env:HTTPS_PROXY"
    Write-Host "  NO_PROXY=$env:NO_PROXY"
    Write-Host ""
    Write-Host "Gradle will auto-detect these settings via settings.gradle and init.gradle"
}

# Handle parameters
if ($Clear) {
    Remove-Item Env:HTTP_PROXY -ErrorAction SilentlyContinue
    Remove-Item Env:http_proxy -ErrorAction SilentlyContinue
    Remove-Item Env:HTTPS_PROXY -ErrorAction SilentlyContinue
    Remove-Item Env:https_proxy -ErrorAction SilentlyContinue
    Remove-Item Env:NO_PROXY -ErrorAction SilentlyContinue
    Remove-Item Env:no_proxy -ErrorAction SilentlyContinue
    Write-Host "Proxy settings cleared." -ForegroundColor Green
} elseif ($StopRelay) {
    Stop-ProxyRelay
} elseif ($Relay) {
    Start-ProxyRelay -UpstreamProxy $ProxyUrl
} else {
    Set-ProxyConfig -Url $ProxyUrl
}
