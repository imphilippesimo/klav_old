#!/bin/sh

echo "Wait service update ..."
while [ 1 -ne $(aws ecs describe-services --services $2 --cluster $1 | jq '.services[0].runningCount') ]
do
  echo "Still waiting for service update"
  sleep 10
done

[ $(aws ecs describe-task-definition --task-definition $3 | jq '.taskDefinition.taskDefinitionArn') = $(aws ecs describe-services --services $2 --cluster $1 | jq '.services[0].taskDefinition') ] &&\
 echo "service successfully update" || exit 1
