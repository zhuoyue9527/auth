#!/bin/sh

. ./env.sh

LANG=en_US

echo "----------------------Process Infomation------------------------------------"
ps -ef|grep $LOGNAME|grep $MAIN_SERVER_OBJECT|grep -v grep
echo "----------------------Process Infomation------------------------------------"
echo ""
echo ""
echo ""

echo "----------------------Thread Status------------------------------------"
ps -xH|grep $LOGNAME|grep $MAIN_SERVER_OBJECT|grep -v grep
echo "----------------------Thread Status------------------------------------"
echo ""
echo ""
echo ""

echo "----------------------Thread Tree------------------------------------"
pstree -p `ps -ef|grep $LOGNAME|grep java|grep -v grep|grep $MAIN_SERVER_OBJECT|awk '{print $2}'`
echo "----------------------Thread Tree------------------------------------"
echo ""
echo ""
echo ""

echo "----------------------Java Infomation------------------------------------"
$JAVA_HOME/bin/jstat -gcutil `ps -ef|grep $LOGNAME|grep java|grep -v grep|grep $MAIN_SERVER_OBJECT|awk '{print $2}'`
$JAVA_HOME/bin/jstat -class `ps -ef|grep $LOGNAME|grep java|grep -v grep|grep $MAIN_SERVER_OBJECT|awk '{print $2}'`
echo "----------------------Java Infomation------------------------------------"
