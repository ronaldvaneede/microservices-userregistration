#!/bin/sh
sudo docker run --name my-rabbit -d -p 15672:15672 -p 5672:5672 -e RABBITMQ_NODENAME=my-rabbit rabbitmq:3-management || sudo docker start my-rabbit
sudo docker run --name my-mongo -d -p 27017:27017 mongo || sudo docker start my-mongo
