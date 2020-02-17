#!/bin/sh
process=`ps -ef | grep -v awk | awk -e '/java.*/ { print $2 }'`
kill ${process} 2>/dev/null
sleep 5s
process=`ps -ef | grep -v awk | awk -e '/java.*/ { print $2 }'`
kill ${process} 2>/dev/null

cd ~
mkdir ~/songs/db -p 2>/dev/null 2>/dev/null
sudo rm /var/log/sander/ -rf
sudo mkdir /var/log/sander/
sudo chown sander:sander /var/log/sander/


echo configuration-service
rm -rf ~/configuration-service
git clone https://github.com/Revenberg/configuration-service.git
cd ~/configuration-service
mvn clean install package
nohup mvn spring-boot:run &
cd ~

echo build spring-boot
rm -rf ~/spring-boot
git clone https://github.com/Revenberg/spring-boot.git

cd ~/spring-boot
mvn clean package

cp ~/spring-boot/songs/src/main/resources/bootstrap.properties ~/spring-boot/songs/
cp ~/spring-boot/songs/src/main/resources/application.yml      ~/spring-boot/songs/

#cp ~/spring-boot/maintain/src/main/resources/bootstrap.properties ~/spring-boot/maintain/
#cp ~/spring-boot/maintain/src/main/resources/application.yml      ~/spring-boot/maintain/

echo build done
cd ~

./start.sh
