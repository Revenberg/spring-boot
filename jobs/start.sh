#!/bin/sh
cd ~
mkdir ~/songs/db 2>/dev/null

cd ~/spring-boot
git pull
mvn clean install package

cd ~
./restart_songs.sh;
sleep 60s

cd ~
./restart_maintain.sh;
