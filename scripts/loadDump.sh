#!/bin/sh
#
# DB must exist, to create DB first :
#-------------------------------------
# CREATE DB :  
# 
# CREATE user and grant privileges:mysqladmin -u root -p create cmsdb
# mysql> CREATE USER 'cmsuser'@'localhost' IDENTIFIED BY 'cmspass';
# mysql> GRANT ALL PRIVILEGES ON cmsdb.* TO 'cmsuser'@'localhost' WITH GRANT OPTION;
#
#

mysql -u root -p -h localhost cmsdb < ../dbDump/08_24_13_14_33_49.sql
