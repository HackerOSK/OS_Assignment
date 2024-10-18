#!/bin/bash

if [ $# -eq 1 ] 
then 
	num=$1
	while [ $num -gt 0 ]
	do
		res=$(( $num % 10 ))
		sum=$(( $sum + $res ))
		num=$(( $num / 10 ))
	done
	echo Sum of digit is $sum
else
	echo Invalide input
fi

