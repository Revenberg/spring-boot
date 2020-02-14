#!/bin/sh
cd ~

cd ~/spring-boot
pull
mvn clean package

./restart_config.sh;
sleep 45s
./restart_songs.sh;
sleep 60s
./restart_maintain.sh;
