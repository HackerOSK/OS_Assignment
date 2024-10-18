#!/bin/sh
# Function to check file type and permissions
check_file() {
        file=$1

        if [ -e "$file" ]; then
        echo "File: $file"

        # Check file type
        if [ -f "$file" ]; then
                echo "Type: Regular file"
        elif [ -d "$file" ]; then
                echo "Type: Directory"
        elif [ -h "$file" ]; then
                echo "Type: Symbolic link"
        elif [ -b "$file" ]; then
                echo "Type: Block Special File"
        elif [ -c "$file" ]; then
                echo "Type: Character Special File"
        else
                echo "Type: Other"
        fi

        # Check file permissions
        permissions=""
        [ -r "$file" ] && permissions="${permissions}readable "
        [ -w "$file" ] && permissions="${permissions}writable "
        [ -x "$file" ] && permissions="${permissions}executable"

        echo "Permissions: $permissions"
        else
        echo "File does not exist."
        fi
}

echo "Enter the file name or path:"
read file

# Call the check_file function with the entered file
check_file "$file"


