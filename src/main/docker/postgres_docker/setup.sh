#!/bin/bash

echo "=========================================="
echo "PostgreSQL Docker Setup for Event Site Manager"
echo "=========================================="

echo ""
echo "Step 1: Stopping existing containers..."
docker-compose down

echo ""
echo "Step 2: Removing existing volumes (this will delete all data)..."
read -p "Press Enter to continue or Ctrl+C to cancel..."
docker volume rm adwiise_agent_db_postgresql_data 2>/dev/null || echo "Volume not found or already removed"

echo ""
echo "Step 3: Starting PostgreSQL with initialization..."
docker-compose up -d

echo ""
echo "Step 4: Waiting for PostgreSQL to be ready..."
sleep 10

echo ""
echo "Step 5: Testing database connection..."
docker-compose exec postgresql psql -U postgres -c "\l"

echo ""
echo "Step 6: Testing application user connection..."
docker-compose exec postgresql psql -U event_site_app -d adwiise_agent_db -c "SELECT current_user, current_database();"

echo ""
echo "=========================================="
echo "Setup Complete!"
echo "=========================================="
echo ""
echo "Database Details:"
echo "- Host: localhost:5432"
echo "- Database: adwiise_agent_db"
echo "- User: event_site_app"
echo "- Password: event_site_app!"
echo ""
echo "Admin User:"
echo "- User: event_site_admin"
echo "- Password: event_site_admin!"
echo ""
echo "Connection URL: jdbc:postgresql://localhost:5432/adwiise_agent_db"
echo ""












