#!/bin/bash

# Check if two arguments are provided
if [ $# -ne 2 ]; then
    echo "Usage: $0 <num1> <num2>"
    exit 1
fi

# Read arguments into variables
num1=$1
num2=$2

# Perform addition
result=$(echo "$num1 + $num2" | bc)

# Output the result
echo "Sum of $num1 and $num2 is: $result"
