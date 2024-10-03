use chrono::prelude::*;
use std::thread;
use std::time::Duration;

fn main() {
    println!("\x1B[2J"); // clear, home

    loop {
        print_time(Local::now());
        println!("");
        thread::sleep(Duration::from_secs(1));
    }
}

fn print_time(now: DateTime<Local>) {
    [now.hour(), now.minute(), now.second()].iter().enumerate().for_each(|(col, n)| {
        print_digits(col * 2, &binary(n / 10));
        print_digits(col * 2 + 1, &binary(n % 10));
    });
}

fn print_digits(col: usize, digits: &str) {
    digits.chars().enumerate().for_each(|(row, c)| {
        print!("\x1B[{};{}H{}", row + 1, col + 1, c)
    })
}

fn binary(n: u32) -> String {
    format!("{n:04b}").replace("0", "-").replace("1", "*")
}
