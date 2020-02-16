#!/bin/sh
cd ~
mkdir ~/songs/db 2>/dev/null

cd ~/spring-boot
git pull
mvn clean package

cd ~
./restart_songs.sh;
sleep 60s
./restart_maintain.sh;
