package com.lqq.bookbar;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Test;
import static java.lang.System.out;

public class Java8Tester {

	@Test
	public void test() {
		List<String> name1 = new ArrayList<>();
		name1.add("Java");
		name1.add("C++");
		name1.add("C");
		name1.add("Python");
		name1.add("Groxy");
		
		List<String> name2 = new ArrayList<>();
		for (int i = 0; i < name1.size(); i++) {
			name2.add(name1.get(i));
		}
		
		new Java7().sortUsingJava7(name1);
		System.out.println(name1);
		
		new Java8().sortUsingJava8(name2);
		System.out.println(name2);
		
		Computable computable = (a,b)->{System.out.println(a+b);return a+b;};
		computable.compute(5, 10);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMethod() {
		List names = new ArrayList();
        
	      names.add("Google");
	      names.add("Runoob");
	      names.add("Taobao");
	      names.add("Baidu");
	      names.add("Sina");
	      names.forEach(System.out::println);
	      
	      
	      
	      final Car car = Car.create(Car::new);
	      final List<Car> cars = Arrays.asList(car);
	      
	      cars.forEach(Car::collide);
	      
	      cars.forEach(Car::repair);
	      
	      final Car police = Car.create( Car::new );
	      cars.forEach( police::follow );
	}
	
	@Test
	public void testStream() {
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		List<String> filtered = strings.stream().filter(s->!s.isEmpty()).collect(Collectors.toList());
		System.out.println(filtered);
		//List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		
		new Random().ints().limit(10).forEach(System.out::println);
		
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		// 获取对应的平方数
		List<Integer> squaresList = numbers.stream().distinct().map( i -> i*i).collect(Collectors.toList());
		System.out.println(squaresList);
		
	}
	
	@Test
	public void testDateTime() {
		LocalDate date = LocalDate.now();
		//System.out.println(date);
		
		LocalTime time = LocalTime.now();
		//System.out.println(time);
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		
		System.out.println("月："+localDateTime.getMonthValue()+"日："+localDateTime.getDayOfMonth()+"小时："+localDateTime.getHour());
		
		//格式化日期
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMdd");
		System.out.println("日期格式化前："+date+"   格式化后："+date.format(ofPattern));
		
		DateTimeFormatter ofPattern1 = DateTimeFormatter.ofPattern("hhmmss");
		System.out.println("时间格式化前："+time+"   格式化后："+time.format(ofPattern1));
		
		DateTimeFormatter ofPattern2 = DateTimeFormatter.ofPattern("yyyyMMdd hhmmss:SSS");
		System.out.println("时戳格式化前："+localDateTime+"   格式化后："+localDateTime.format(ofPattern2));
		
		// 获取当前时间日期
		ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
		System.out.println("date1: " + date1);
        
		ZoneId id = ZoneId.of("Europe/Paris");
		System.out.println("ZoneId: " + id);
        
		ZoneId currentZone = ZoneId.systemDefault();
		System.out.println("当期时区: " + currentZone);
		
		ZonedDateTime dateTime = ZonedDateTime.now();
		System.out.println(dateTime.getZone()+"--"+dateTime.getOffset()+"--"+dateTime.plusMonths(1).getMonthValue());
	}
	
	@Test
	public void testBase64() {
		 try {
		        
	         // 使用基本编码
	         String base64encodedString = Base64.getEncoder().encodeToString("runoob?java8".getBytes("utf-8"));
	         System.out.println("Base64 编码字符串 (基本) :" + base64encodedString);
	        
	         // 解码
	         byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
	        
	         System.out.println("原始字符串: " + new String(base64decodedBytes, "utf-8"));
	         base64encodedString = Base64.getUrlEncoder().encodeToString("TutorialsPoint?java8".getBytes("utf-8"));
	         System.out.println("Base64 编码字符串 (URL) :" + base64encodedString);
	        
	         StringBuilder stringBuilder = new StringBuilder();
	        
	         stringBuilder.append("sdfsdfssfsdfdsfds");
			/*
			 * for (int i = 0; i < 10; ++i) {
			 * stringBuilder.append(UUID.randomUUID().toString()); }
			 */
	        
	         byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
	         String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
	         System.out.println("Base64 编码字符串 (MIME) :" + mimeEncodedString);
	         
	      }catch(UnsupportedEncodingException e){
	         System.out.println("Error :" + e.getMessage());
	      }
		 
		 out.println();
		 
	}
	
	interface Computable {
		int compute(int a ,int b);
	}
	
	class Java7{
		/**
		 * 使用java7来执行排序
		 * @param names
		 */
		public void sortUsingJava7(List<String> names) {
			Collections.sort(names, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
		}
	}
	
	class Java8{
		/**
		 * 使用java8来执行排序
		 * @param names
		 */
		public void sortUsingJava8(List<String> names) {
			Collections.sort(names,(s1,s2)->{return s1.compareTo(s2);});
		}
	}

	
	@FunctionalInterface
	interface Supplier<T> {
	    T get();
	}
	 
	static class Car {
	    //Supplier是jdk1.8的接口，这里和lamda一起使用了
	    public static Car create(final Supplier<Car> supplier) {
	        return supplier.get();
	    }
	 
	    public static void collide(final Car car) {
	        System.out.println("Collided " + car.toString());
	    }
	 
	    public void follow(final Car another) {
	        System.out.println("Following the " + another.toString());
	    }
	 
	    public void repair() {
	        System.out.println("Repaired " + this.toString());
	    }
	}
}
