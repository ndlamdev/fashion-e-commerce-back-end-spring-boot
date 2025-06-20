docker run -d --name my-redis -p 6379:6379 -v ./data:/data redis redis-server --appendonly yes
