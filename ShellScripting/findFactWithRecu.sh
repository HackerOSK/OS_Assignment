#!/bin/bash


facto()
{
	if [ $1 -le 1 ]
	then
		echo 1
	else
		echo $(( $(facto $(( $1 - 1 )) ) * $1 ))
	fi
}

ans=$( facto $1 )
echo $ans

