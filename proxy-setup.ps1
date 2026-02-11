# Proxy Setup Helper Script for PowerShell
# Dot-source this script to configure proxy environment variables for Gradle builds
#
# Usage:
#   . .\proxy-setup.ps1 [proxy-url]
#   . .\proxy-setup.ps1 http://proxy.example.com:8080
#   . .\proxy-setup.ps1 http://user:pass@proxy.example.com:8080
#
# Or set the proxy URL first:
#   $env:PROXY_URL = "http://proxy.example.com:8080"
#   . .\proxy-setup.ps1

param(
    [Parameter(Position=0)]
    [string]$ProxyUrl
)

function Set-ProxyConfig {
    param(
        [string]$Url
    )

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
        Write-Host "To clear proxy settings:"
        Write-Host "  . .\proxy-setup.ps1 --clear"
        Write-Host ""
        Write-Host "Current proxy settings:"
        Write-Host "  HTTP_PROXY:  $($env:HTTP_PROXY ?? '<not set>')"
        Write-Host "  HTTPS_PROXY: $($env:HTTPS_PROXY ?? '<not set>')"
        Write-Host "  NO_PROXY:    $($env:NO_PROXY ?? '<not set>')"
        return
    }

    if ($Url -eq "--clear" -or $Url -eq "-c") {
        Remove-Item Env:HTTP_PROXY -ErrorAction SilentlyContinue
        Remove-Item Env:http_proxy -ErrorAction SilentlyContinue
        Remove-Item Env:HTTPS_PROXY -ErrorAction SilentlyContinue
        Remove-Item Env:https_proxy -ErrorAction SilentlyContinue
        Remove-Item Env:NO_PROXY -ErrorAction SilentlyContinue
        Remove-Item Env:no_proxy -ErrorAction SilentlyContinue
        Write-Host "Proxy settings cleared." -ForegroundColor Green
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

# Configure proxy if URL provided
Set-ProxyConfig -Url $ProxyUrl
