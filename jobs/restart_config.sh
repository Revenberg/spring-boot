
#!/bin/sh
process=`ps -ef | grep -v awk | awk -e '/java.*configuration-service/ { print $2 }'`
kill ${process} 2>/dev/null
sleep 5s
process=`ps -ef | grep -v awk | awk -e '/java.*configuration-service/ { print $2 }'`
kill ${process} 2>/dev/null

echo start configuration-service

cd ~/configuration-service
git pull
mvn clean package
nohup mvn spring-boot:run > /var/log/sander/configuration-service.log &

cd ~
