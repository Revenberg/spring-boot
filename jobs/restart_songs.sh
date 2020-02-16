#!/bin/sh
process=`ps -ef | grep -v awk | awk -e '/java.*songs/ { print $2 }'`
kill ${process} 2>/dev/null
sleep 5s
process=`ps -ef | grep -v awk | awk -e '/java.*songs/ { print $2 }'`
kill ${process} 2>/dev/null

echo start songs

cp ~/spring-boot/songs/src/main/resources/bootstrap.properties ~/songs/
cp ~/spring-boot/songs/src/main/resources/application.yml      ~/songs/

cd ~/spring-boot
nohup mvn spring-boot:run  -rf songs > /var/log/sander/songs.log & 

cd ~
