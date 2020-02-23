
#!/bin/sh
process=`ps -ef | grep -v awk | awk -e '/java.*maintain/ { print $2 }'`
kill ${process} 2>/dev/null
sleep 5s
process=`ps -ef | grep -v awk | awk -e '/java.*maintain/ { print $2 }'`
kill ${process} 2>/dev/null

echo start maintain

cp ~/spring-boot/maintain/src/main/resources/bootstrap.properties ~/maintain/
cp ~/spring-boot/maintain/src/main/resources/application.yml      ~/maintain/

cd ~/spring-boot/maintain

nohup mvn spring-boot:run -rf maintain > /var/log/sander/maintain.log & 

cd ~
