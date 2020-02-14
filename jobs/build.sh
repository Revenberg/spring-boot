cd ~
sudo rm /var/log/sander/ -rf
sudo mkdir /var/log/sander/
sudo chown sander:sander /var/log/sander/

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
