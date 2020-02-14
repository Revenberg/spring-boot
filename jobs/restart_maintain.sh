
#!/bin/sh
process=`ps -ef | grep -v awk | awk -e '/java.*maintain/ { print $2 }'`
kill ${process}
sleep 5s
process=`ps -ef | grep -v awk | awk -e '/java.*maintain/ { print $2 }'`
kill ${process}

echo start maintain

cp ~/spring-boot/maintain/src/main/resources/bootstrap.properties ~/maintain/
cp ~/spring-boot/maintain/src/main/resources/application.yml      ~/maintain/

cd ~/spring-boot/maintain
nohup mvn spring-boot:run > /var/log/sander/maintain.log & 

cd ~
