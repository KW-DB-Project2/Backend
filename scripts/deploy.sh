#!/bin/bash
PROJECT_ROOT="/home/ubuntu/Backend"

#로그파일 초기화
> $PROJECT_ROOT/deploy.log
> $PROJECT_ROOT/deploy_err.log

BUILD_JAR=$(ls $PROJECT_ROOT/build/libs/db-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo ">>> build 파일명: $JAR_NAME" >> $PROJECT_ROOT/deploy.log

echo ">>> build 파일 복사" >> $PROJECT_ROOT/deploy.log
DEPLOY_PATH=$PROJECT_ROOT
cp $BUILD_JAR $DEPLOY_PATH

echo ">>> 현재 실행중인 애플리케이션 pid 확인" >> $PROJECT_ROOT/deploy.log
CURRENT_PID=$(pgrep -fl $JAR_NAME | grep java | awk '{print $1}')

if [ -z $CURRENT_PID ]
then
  echo ">>> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $PROJECT_ROOT/deploy.log
else
  echo ">>> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH/$JAR_NAME
echo ">>> DEPLOY_JAR 배포"    >> $PROJECT_ROOT/deploy.log
chmod +x $DEPLOY_JAR
nohup java -jar $DEPLOY_JAR >> $PROJECT_ROOT/deploy.log 2>$PROJECT_ROOT/deploy_err.log &