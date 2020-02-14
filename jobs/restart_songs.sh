#!/bin/sh
process=`ps -ef | grep -v awk | awk -e '/java.*songs/ { print $2 }'`
kill ${process}
sleep 5s
process=`ps -ef | grep -v awk | awk -e '/java.*songs/ { print $2 }'`
kill ${process}

echo start songs

cp ~/spring-boot/songs/src/main/resources/bootstrap.properties ~/songs/
cp ~/spring-boot/songs/src/main/resources/application.yml      ~/songs/

cd ~/spring-boot/songs
nohup mvn spring-boot:run > /var/log/sander/songs.log & 

cd ~
