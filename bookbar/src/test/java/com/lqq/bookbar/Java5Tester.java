package com.lqq.bookbar;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class Java5Tester {

	@Test(timeout=1000)
	public void testT() {
		List<String> list = new ArrayList<>();
		list.add("JAVA");
		list.add("C");
		list.add("C++");
		list.add("Python");
		Collections.sort(list, (a,b)->b.compareTo(a));
		print(list);
		
		//List<Integer> list2 = {[1,2,32,4,34]};
		
	}
	@Test
	public void testEnum() {
		System.out.println(Car.sanlun.toString());
	}

	public void print(List<?> list) {
		for (Object object : list) {
			System.out.println(object);
		}
	}
	
	public <T extends Integer> double sum(T a1,T a2) {
		return a1.doubleValue()+a2.doubleValue();
	}
	
	enum Car{
		sanlun,silun,duolun
	}
	class Fan<T>{
		private T t;
		
		public Fan(T t2) {
			this.t = t2;
		}
		
		public T geT() {
			return this.t;
		}
	}
	
	
}
