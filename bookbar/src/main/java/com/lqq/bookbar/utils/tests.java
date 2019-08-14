package com.lqq.bookbar.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class tests {

	
	public static void main(String[] args) {
		final LocalDate localDate1 = LocalDate.now().plusDays(1);
		LocalDate localDate2 = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		localDate2 = localDate2.plusDays(1);
		System.out.println("localDate1:"+localDate1.format(formatter));
		System.out.println("localDate2:"+localDate2.format(formatter));
		if (localDate1.isAfter(localDate2)) {
			
			System.out.println("hahah");
		}
	}
}
