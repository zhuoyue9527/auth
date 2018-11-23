#!/bin/bash

. ./env.sh

if [ -f "$HFP_SERVER_PID" ]
then
	  	echo "Stop $MAIN_SERVER_OBJECT ... ..."
      echo "Killing: `cat $HFP_SERVER_PID`"
      kill -9 `cat $HFP_SERVER_PID`
      rm $HFP_SERVER_PID
else
      echo "Kill failed: \$HFP_SERVER_PID not set"
fi