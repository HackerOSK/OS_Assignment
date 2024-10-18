#!/bin/bash

echo Choose operation from following :
echo a = Get date
echo b = Get List of file

read choice
case $choice in
	a) date;;
	b) ls;;
	*) echo "Not a valid Input"
esac

