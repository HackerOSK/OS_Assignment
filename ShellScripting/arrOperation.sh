#!/bin/bash


getAvg(){
a=$#
sum=0
for i in $@
do
	sum=$(( $sum + $i ))
avg=$(( $sum / $a ))
done
return $avg
}


getMax(){
	echo Enter the number 
	read num
	max=-2147483647

	while [ $num -gt 0 ]
	do
		res=$(( $num % 10 ))
		if [ $res -gt $max ]
		then
			max=$res
		fi
		num=$(( $num / 10 ))
	done

return $max
}

getMin(){
        echo Enter the number
        read num
        min=2147483647

        while [ $num -gt 0 ]
        do
                res=$(( $num % 10 ))
                if [ $res -lt $max ]
                then
                        min=$res
                fi
                num=$(( $num / 10 ))
        done   
return $min
}

loop=1
while [ $loop -eq 1 ]
do
        echo Choose the operation from the following on given array
        echo $*
        echo a] Get Average
        echo b] Get Max Element
        echo c] Get Min Element
        echo d] Exit
        read choice
        case $choice in
                a) getAvg $@
                        echo Average of value : $?;;
                b) getMax $@
                        echo Maximum value : $?;;
                c) getMin $@
                        echo Minimum value : $?;;
                d) loop=0
                        echo "Thank You Visit Again!!";;
                *) echo "Invalide Input";;
        esac
done

