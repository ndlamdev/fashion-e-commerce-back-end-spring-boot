mysqldump -uroot -proot fashion_e_commerce > mysql/database.sql
docker exec -i authentication_mysql mysql -ufashion_e_commerce -pdoan_xem fashion_e_commerce < mysql/database.sql