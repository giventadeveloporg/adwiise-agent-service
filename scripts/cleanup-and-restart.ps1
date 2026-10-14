# Stop the local app container and start it again from the repository root.
# Secrets stay in src/main/docker/Docker_Local/.env (gitignored).

$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..")
Set-Location $repoRoot

$envFile = Join-Path $repoRoot "src\main\docker\Docker_Local\.env"
if (-not (Test-Path $envFile)) {
    Write-Error "Missing gitignored env file: $envFile"
    exit 1
}

Write-Host "Stopping and removing old containers..." -ForegroundColor Yellow
docker compose down

$oldContainers = docker ps -a --format "{{.Names}}" | Select-String -Pattern "recursing|poitras"
if ($oldContainers) {
    Write-Host "Found old containers to remove:" -ForegroundColor Yellow
    $oldContainers | ForEach-Object {
        Write-Host "  Removing: $_" -ForegroundColor Yellow
        docker rm -f $_
    }
} else {
    Write-Host "No old containers found with 'recursing' or 'poitras' in the name." -ForegroundColor Green
}

Write-Host "`nCleaning up stopped containers..." -ForegroundColor Yellow
docker container prune -f

Write-Host "`nRebuilding and starting project 'adwiise-agent-service' from $repoRoot..." -ForegroundColor Green
docker compose up -d --build

Write-Host "`nDone. The container should appear as 'adwiise-agent-service' in Docker Desktop." -ForegroundColor Green
Write-Host "Check status with: docker ps" -ForegroundColor Cyan
