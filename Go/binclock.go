package main

import (
	"fmt"
	"strings"
	"time"
)

func main() {
	fmt.Print("\033[2J") // clear, home

	for {
		printTime(time.Now())
		println("")
		time.Sleep(time.Second)
	}
}

func printTime(now time.Time) {
	for col, n := range []int{now.Hour(), now.Minute(), now.Second()} {
		printDigits(col*2, binary(n/10))
		printDigits(col*2+1, binary(n%10))
	}
}

func printDigits(col int, digits string) {
	for row, c := range digits {
		fmt.Printf("\033[%v;%vH%c", row+1, col+1, c)
	}
}

func binary(i int) string {
	return strings.Replace(strings.Replace(fmt.Sprintf("%04b", i), "0", "-", -1), "1", "*", -1)
}
