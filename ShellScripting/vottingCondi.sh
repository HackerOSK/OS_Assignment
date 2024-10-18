#!/bin/bash
echo Hello User Welcome to Election
echo Enter your age
read age

if [ $age -ge 18 ]
then
	echo You are Eligible for Vote
else
	echo You are not Eligible for vote
fi


