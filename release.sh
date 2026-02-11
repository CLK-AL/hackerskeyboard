#!/bin/bash
# release.sh - Create a release with git tag in yyyy.MM.dd.HH.mm.ss format
#
# Usage:
#   ./release.sh           # Create tag and push
#   ./release.sh --dry-run # Preview without pushing
#   ./release.sh --local   # Create tag locally without pushing

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo_info() { echo -e "${GREEN}[INFO]${NC} $1"; }
echo_warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
echo_error() { echo -e "${RED}[ERROR]${NC} $1"; }
echo_step() { echo -e "${BLUE}[STEP]${NC} $1"; }

# Parse arguments
DRY_RUN=false
LOCAL_ONLY=false
CUSTOM_TAG=""

while [[ $# -gt 0 ]]; do
    case $1 in
        --dry-run|-n)
            DRY_RUN=true
            shift
            ;;
        --local|-l)
            LOCAL_ONLY=true
            shift
            ;;
        --tag|-t)
            CUSTOM_TAG="$2"
            shift 2
            ;;
        --help|-h)
            echo "Usage: $0 [options]"
            echo ""
            echo "Options:"
            echo "  --dry-run, -n    Preview without making changes"
            echo "  --local, -l      Create tag locally without pushing"
            echo "  --tag, -t TAG    Use custom tag instead of timestamp"
            echo "  --help, -h       Show this help message"
            echo ""
            echo "Examples:"
            echo "  $0                    # Create tag yyyy.MM.dd.HH.mm.ss and push"
            echo "  $0 --dry-run          # Preview what would happen"
            echo "  $0 --tag v1.43.0      # Use custom tag"
            exit 0
            ;;
        *)
            echo_error "Unknown option: $1"
            exit 1
            ;;
    esac
done

# Generate timestamp tag: yyyy.MM.dd.HH.mm.ss
if [ -n "$CUSTOM_TAG" ]; then
    TAG_NAME="$CUSTOM_TAG"
else
    TAG_NAME=$(date +"%Y.%m.%d.%H.%M.%S")
fi

# Get base version from build.gradle
BASE_VERSION=$(grep 'baseVersionName' app/build.gradle 2>/dev/null | sed 's/.*"\(.*\)".*/\1/' || echo "v1.42.2ak")

echo ""
echo_info "=== Hacker's Keyboard Release Script ==="
echo ""
echo_info "Base Version: $BASE_VERSION"
echo_info "Release Tag:  $TAG_NAME"
echo_info "Full Version: ${BASE_VERSION}-${TAG_NAME}"
echo ""

# Check for uncommitted changes
if [ -n "$(git status --porcelain)" ]; then
    echo_warn "You have uncommitted changes:"
    git status --short
    echo ""
    if [ "$DRY_RUN" = false ]; then
        read -p "Continue anyway? (y/N) " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            echo_error "Aborted."
            exit 1
        fi
    fi
fi

# Check if tag already exists
if git rev-parse "$TAG_NAME" >/dev/null 2>&1; then
    echo_error "Tag '$TAG_NAME' already exists!"
    echo_info "Use --tag to specify a different tag name"
    exit 1
fi

# Get current branch
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
echo_info "Current branch: $CURRENT_BRANCH"

# Get commit info
COMMIT_SHA=$(git rev-parse --short HEAD)
COMMIT_MSG=$(git log -1 --pretty=%s)
echo_info "Latest commit: $COMMIT_SHA - $COMMIT_MSG"
echo ""

if [ "$DRY_RUN" = true ]; then
    echo_warn "=== DRY RUN MODE ==="
    echo ""
    echo_step "Would create tag: $TAG_NAME"
    echo_step "Would tag commit: $COMMIT_SHA"
    if [ "$LOCAL_ONLY" = false ]; then
        echo_step "Would push tag to origin"
    fi
    echo ""
    echo_info "Tag message would be:"
    echo "  Release $TAG_NAME"
    echo "  Version: ${BASE_VERSION}-${TAG_NAME}"
    echo "  Commit: $COMMIT_SHA"
    echo ""
    echo_info "Run without --dry-run to create the release"
    exit 0
fi

# Create annotated tag
echo_step "Creating tag '$TAG_NAME'..."

TAG_MESSAGE="Release $TAG_NAME

Version: ${BASE_VERSION}-${TAG_NAME}
Commit: $COMMIT_SHA
Date: $(date -u +"%Y-%m-%d %H:%M:%S UTC")
Branch: $CURRENT_BRANCH"

git tag -a "$TAG_NAME" -m "$TAG_MESSAGE"
echo_info "Tag created successfully"

if [ "$LOCAL_ONLY" = true ]; then
    echo ""
    echo_info "=== Local Tag Created ==="
    echo_info "Tag '$TAG_NAME' created locally"
    echo_info "To push later, run: git push origin $TAG_NAME"
    exit 0
fi

# Push tag to remote
echo_step "Pushing tag to origin..."
git push origin "$TAG_NAME"
echo_info "Tag pushed successfully"

echo ""
echo_info "=== Release Created Successfully ==="
echo ""
echo_info "Tag:     $TAG_NAME"
echo_info "Version: ${BASE_VERSION}-${TAG_NAME}"
echo_info "Commit:  $COMMIT_SHA"
echo ""
echo_info "GitHub Actions will now build and publish the release."
echo_info "Check progress at: https://github.com/\$(git remote get-url origin | sed 's/.*github.com[:/]//;s/.git$//')/actions"
echo ""

# Show tag info
echo_info "Tag details:"
git show "$TAG_NAME" --quiet
