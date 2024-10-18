#!/bin/bash

number=$1

num=1
while [ $num -le 10 ]
do
	echo $number X $num = $(($number * $num))
	let num++
done


