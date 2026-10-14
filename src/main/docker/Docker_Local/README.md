# Local Docker (Spring Boot app)

Run Compose from the **repository root**. `docker-compose.yml` there is the default file, so no `-f` flag and no `cd` into this folder.

Secrets stay in **`.env` in this folder** (`src/main/docker/Docker_Local/.env`). That file is gitignored. Do not copy it to the repo root and do not put usernames, passwords, API keys, or tokens in this README.

The root `Dockerfile` is the production image. Local builds use `Dockerfile.local` in this folder.

## Prerequisites

- Docker Desktop
- PostgreSQL stack running (`src/main/docker/postgres_docker`)
- External networks: `adwiise_agent_db_default`, `adwiise-agent-batch-jobs_batch-jobs-network`
- Built JAR at repo root: `target/adwiise-agent-service-0.0.1-SNAPSHOT.jar` (run `mvn package -DskipTests` from the project root if missing)

## Configure

Edit `.env` in this directory. Key settings:

| Variable                                         | Purpose                                                                                     |
| ------------------------------------------------ | ------------------------------------------------------------------------------------------- |
| `RDS_ENDPOINT`                                   | Postgres hostname on Docker network (e.g. `adwiise_agent_db-postgresql-1`)                  |
| `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`          | Database connection (set values in `.env` only)                                             |
| `JWT_API_AUTH_USERNAME`, `JWT_API_AUTH_PASSWORD` | Service JWT login (must match frontend `API_JWT_USER` / `API_JWT_PASS`; set in `.env` only) |
| `SPRING_PROFILES_ACTIVE`                         | Compose sets `local-postgres-cache` on the container                                        |

## Start / restart

From the repository root:

```powershell
docker compose up -d --build
```

Or:

```powershell
.\scripts\cleanup-and-restart.ps1
```

## Stop

```powershell
docker compose down
```

## Health check

```powershell
curl.exe http://127.0.0.1:8080/management/health
```
