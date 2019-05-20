#!/bin/sh

CREATE=0
sleep 30
echo "creating AWS CloudFormation stacks..."

for result in $(aws cloudformation list-stacks --stack-status-filter CREATE_COMPLETE | jq .StackSummaries[].StackName)
do
  [ $result = "\"$1\"" ] && CREATE=1
done

if [ $CREATE -eq 0 ]
then
  aws cloudformation create-stack --stack-name $1 --template-body file://$(pwd)/main.yaml --capabilities CAPABILITY_NAMED_IAM --parameters ParameterKey=Size,ParameterValue=$2
  STATUS=$(aws cloudformation describe-stacks --stack-name $1 | jq '.Stacks[0].StackStatus')

  while [ $STATUS != "\"CREATE_COMPLETE\""  ]
  do
    echo "Still waiting stack creation..."
    sleep 20
    STATUS=$(aws cloudformation describe-stacks --stack-name $1 | jq '.Stacks[0].StackStatus')
    [ $STATUS = "\"ROLLBACK_COMPLETE\"" ] && exit 1
  done

  echo "Stack $1 creation complete"
else
  echo "Stack $1 already exist delete it before "
fi

#aws cloudformation describe-stacks --stack-name $1 | jq '.Stacks[].Outputs'
#mkdir tmp
echo " Little insight about the service :"
echo " ###### the load balancer url ######"
aws cloudformation describe-stacks --stack-name review-${CI_COMMIT_SHORT_SHA} | jq '.Stacks[].Outputs[] | select( .OutputKey == "LoadBalancerUrl" ) | .OutputValue'
echo " ###### the instance dns public name ######"
aws ec2 describe-instances --filters "Name=tag:Name,Values=review-${CI_COMMIT_SHORT_SHA} ECS host" | jq '.Reservations[].Instances[0].PublicDnsName'
