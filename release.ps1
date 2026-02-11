# release.ps1 - Create a release with git tag in yyyy.MM.dd.HH.mm.ss format
#
# Usage:
#   .\release.ps1              # Create tag and push
#   .\release.ps1 -DryRun      # Preview without pushing
#   .\release.ps1 -LocalOnly   # Create tag locally without pushing
#   .\release.ps1 -Tag "v1.43" # Use custom tag

param(
    [switch]$DryRun,
    [switch]$LocalOnly,
    [string]$Tag,
    [switch]$Help
)

$ErrorActionPreference = "Stop"

function Write-Info { param($msg) Write-Host "[INFO] $msg" -ForegroundColor Green }
function Write-Warn { param($msg) Write-Host "[WARN] $msg" -ForegroundColor Yellow }
function Write-Err { param($msg) Write-Host "[ERROR] $msg" -ForegroundColor Red }
function Write-Step { param($msg) Write-Host "[STEP] $msg" -ForegroundColor Blue }

if ($Help) {
    Write-Host "Usage: .\release.ps1 [options]"
    Write-Host ""
    Write-Host "Options:"
    Write-Host "  -DryRun       Preview without making changes"
    Write-Host "  -LocalOnly    Create tag locally without pushing"
    Write-Host "  -Tag TAG      Use custom tag instead of timestamp"
    Write-Host "  -Help         Show this help message"
    Write-Host ""
    Write-Host "Examples:"
    Write-Host "  .\release.ps1                  # Create tag yyyy.MM.dd.HH.mm.ss and push"
    Write-Host "  .\release.ps1 -DryRun          # Preview what would happen"
    Write-Host "  .\release.ps1 -Tag 'v1.43.0'   # Use custom tag"
    exit 0
}

# Generate timestamp tag: yyyy.MM.dd.HH.mm.ss
if ($Tag) {
    $TagName = $Tag
} else {
    $TagName = Get-Date -Format "yyyy.MM.dd.HH.mm.ss"
}

# Get base version from build.gradle
$BaseVersion = "v1.42.2ak"
$buildGradle = Get-Content "app/build.gradle" -Raw -ErrorAction SilentlyContinue
if ($buildGradle -match 'baseVersionName\s*=\s*"([^"]+)"') {
    $BaseVersion = $Matches[1]
}

Write-Host ""
Write-Info "=== Hacker's Keyboard Release Script ==="
Write-Host ""
Write-Info "Base Version: $BaseVersion"
Write-Info "Release Tag:  $TagName"
Write-Info "Full Version: ${BaseVersion}-${TagName}"
Write-Host ""

# Check for uncommitted changes
$status = git status --porcelain
if ($status) {
    Write-Warn "You have uncommitted changes:"
    git status --short
    Write-Host ""
    if (-not $DryRun) {
        $response = Read-Host "Continue anyway? (y/N)"
        if ($response -notmatch '^[Yy]') {
            Write-Err "Aborted."
            exit 1
        }
    }
}

# Check if tag already exists
$tagExists = git rev-parse $TagName 2>$null
if ($LASTEXITCODE -eq 0) {
    Write-Err "Tag '$TagName' already exists!"
    Write-Info "Use -Tag to specify a different tag name"
    exit 1
}

# Get current branch
$CurrentBranch = git rev-parse --abbrev-ref HEAD
Write-Info "Current branch: $CurrentBranch"

# Get commit info
$CommitSha = git rev-parse --short HEAD
$CommitMsg = git log -1 --pretty=%s
Write-Info "Latest commit: $CommitSha - $CommitMsg"
Write-Host ""

if ($DryRun) {
    Write-Warn "=== DRY RUN MODE ==="
    Write-Host ""
    Write-Step "Would create tag: $TagName"
    Write-Step "Would tag commit: $CommitSha"
    if (-not $LocalOnly) {
        Write-Step "Would push tag to origin"
    }
    Write-Host ""
    Write-Info "Tag message would be:"
    Write-Host "  Release $TagName"
    Write-Host "  Version: ${BaseVersion}-${TagName}"
    Write-Host "  Commit: $CommitSha"
    Write-Host ""
    Write-Info "Run without -DryRun to create the release"
    exit 0
}

# Create annotated tag
Write-Step "Creating tag '$TagName'..."

$TagMessage = @"
Release $TagName

Version: ${BaseVersion}-${TagName}
Commit: $CommitSha
Date: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss") UTC
Branch: $CurrentBranch
"@

git tag -a $TagName -m $TagMessage
if ($LASTEXITCODE -ne 0) {
    Write-Err "Failed to create tag"
    exit 1
}
Write-Info "Tag created successfully"

if ($LocalOnly) {
    Write-Host ""
    Write-Info "=== Local Tag Created ==="
    Write-Info "Tag '$TagName' created locally"
    Write-Info "To push later, run: git push origin $TagName"
    exit 0
}

# Push tag to remote
Write-Step "Pushing tag to origin..."
git push origin $TagName
if ($LASTEXITCODE -ne 0) {
    Write-Err "Failed to push tag"
    exit 1
}
Write-Info "Tag pushed successfully"

Write-Host ""
Write-Info "=== Release Created Successfully ==="
Write-Host ""
Write-Info "Tag:     $TagName"
Write-Info "Version: ${BaseVersion}-${TagName}"
Write-Info "Commit:  $CommitSha"
Write-Host ""
Write-Info "GitHub Actions will now build and publish the release."
Write-Host ""

# Show tag info
Write-Info "Tag details:"
git show $TagName --quiet
