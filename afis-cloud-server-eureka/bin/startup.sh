#!/bin/bash

. ./env.sh

if [ -f "$HFP_SERVER_PID" ]
then
	echo "Server: $MAIN_SERVER_OBJECT  is running... ..., if you want to startup, shutdown firstly. Or restart.sh"
	exit 0
fi


echo "____________CLASSPATH_______________"
echo $CLASSPATH
echo "____________CLASSPATH_______________"


cd $HFP_SERVER_HOME/bin


echo "Start $MAIN_SERVER_OBJECT ... ...."
nohup $JAVA_HOME/bin/java $MAIN_SERVRt_OPTS $MAIN_SERVER_OBJECT eureka\
                 >>/dev/null 2>&1 &

if [ ! -z "$HFP_SERVER_PID" ]
then
        echo $! > $HFP_SERVER_PID
fi

echo "$MAIN_SERVER_OBJECT PID: "`cat       $HFP_SERVER_PID`