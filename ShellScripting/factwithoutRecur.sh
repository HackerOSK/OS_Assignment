#!/bin/bash

facto()
{
	local num=$1
        local fact=1
	if [ $num -le 1 ]
	then
		return $fact
	else
		while [ $num -gt 0 ]
		do
			fact=$(( $fact * $num ))
			let num--
		done
		echo $fact
	fi
}

 ans=$( facto $1 )
 echo Factorial of given number $1 is $ans

