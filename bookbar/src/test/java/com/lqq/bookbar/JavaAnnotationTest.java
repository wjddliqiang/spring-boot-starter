package com.lqq.bookbar;

import static org.junit.Assert.*;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import oracle.net.aso.p;

/**
 * 使用java注解演示一个orm框架注解来根据对象生成sql语句
 * @author lenovo
 *
 */
public class JavaAnnotationTest {

	@Test
	public void test() {
		
		System.out.println(query(new Person("liqiqiang", "18")));;
	}

	public String query(Person person) {
		Class class1 = person.getClass();
		StringBuilder sBuilder = new StringBuilder();
		if (class1.isAnnotationPresent(Table.class)) {
			Table table = (Table)class1.getAnnotation(Table.class);
			String t1 = table.value();
			String t2 = table.isDistinct()?"distinct":"";
			sBuilder.append("select "+t2+" * from "+t1+" where 1=1");
			Field[] fields = class1.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String fieldvalue = "";
					String getMethString = "get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
					try {
						Method method = class1.getMethod(getMethString);
						fieldvalue = (String) method.invoke(person);
					} catch (Exception e) {
						e.printStackTrace();
					}
					sBuilder.append(" and "+column.value()+"="+fieldvalue);
				}
			}
		}
		return sBuilder.toString();
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface Table{
		String value();
		boolean isDistinct();
	}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface Column{
		String value();
	}
	
	@Table(value="table1",isDistinct=true)
	class Person{
		@Column("name")
		private String name;
		@Column("age")
		private String age;

		public Person(String name,String age) {
			this.name = name;
			this.age = age;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}
		
	}
}
