#!/bin/bash

function test() {
    file=$1
    cnt=0
    n=$(wc -l "$file" | awk '{print $1}')
    let ok=$n
    while read -r line; do
        if ! result=$(echo "$line" | make -s run); then
            echo "Runtime error on test $((cnt+1)):"
            echo "	$line"
        fi
        if [[ $result != "$2" ]]; then
            echo "Wrong answer on test $((cnt+1)):"
            echo "	$line"
        fi
        let cnt=$cnt+1
        if [[ $((cnt % 10)) -eq 0 ]]; then
            echo "	Completed $cnt of $n"
        fi
    done < "$file"
    let ok=$n-$(wc -l log)
    echo "--- Passed $((ok+1)) of $n tests ---"
}

echo "Building solution..."
make

echo "Running equality tests:"
test "./tests/eq.txt" "Equal"

echo "Running inequality tests:"
test "./tests/neq.txt" "Unequal"