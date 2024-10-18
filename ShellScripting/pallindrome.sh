#!/bin/bash


	str=$1
	left=0
	right=$((${#str} - 1))
     while [[ $left -lt $right ]]; do
        if [[ ${str:$left:1} != ${str:$right:1} ]]; then
            echo "Not a palindrome"
            exit 0
        fi
        ((left++))
        ((right--))
    done
    echo "Palindrome"
    exit 0


