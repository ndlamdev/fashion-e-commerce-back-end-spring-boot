docker run -d --name mysql_server -e MYSQL_ROOT_PASSWORD=root -v ~/data:/var/lib/mysql -p 3306:3306 mysql:8
