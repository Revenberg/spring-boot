
#!/bin/sh
process=`ps -ef | grep -v awk | awk -e '/java.*configuration-service/ { print $2 }'`
kill ${process}
sleep 5s
process=`ps -ef | grep -v awk | awk -e '/java.*configuration-service/ { print $2 }'`
kill ${process}

echo start configuration-service

cd ~/spring-boot/configuration-service
nohup mvn spring-boot:run > /var/log/sander/configuration-service.log &

cd ~
