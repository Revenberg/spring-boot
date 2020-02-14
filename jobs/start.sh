#!/bin/sh
cd ~
mkdir ~ songs 2>/dev/null

cd ~/spring-boot
pull
mvn clean package

cd ~
./restart_config.sh;
sleep 45s
./restart_songs.sh;
sleep 60s
./restart_maintain.sh;
