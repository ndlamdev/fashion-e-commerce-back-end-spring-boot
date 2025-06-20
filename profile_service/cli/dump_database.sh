mysqldump -uroot -p123456@Gio fashion_e_commerce > mysql/database.sql
docker exec -i profile_mysql mysql -ufashion_e_commerce -pell_doan fashion_e_commerce < ../mysql/database.sql