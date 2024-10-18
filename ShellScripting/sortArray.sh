#!/bin/bash
# Initialize the index and the array
j=0
array=()
for i in "$@"
do
    array[$j]=$i
    let j++
done

echo "Array before sorting: "
echo ${array[*]}

len=$(( ${#array[@]} ))

# Bubble sort algorithm to sort the array
for ((i=0; i<$len; i++))
do
    for ((j=i+1; j<$len; j++))
    do
        if [ ${array[$i]} -gt ${array[$j]} ]
        then
            temp=${array[$i]}
            array[$i]=${array[$j]}
            array[$j]=$temp
        fi
    done
done

echo "Array after sorting: "
echo ${array[*]}





