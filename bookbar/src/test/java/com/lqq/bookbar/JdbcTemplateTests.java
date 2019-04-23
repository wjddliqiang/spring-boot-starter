package com.lqq.bookbar;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.lqq.bookbar.utils.PrimaryKeyHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTemplateTests {

	@Autowired
	private JdbcTemplate jdbc;
	private String pkid;
	
	@Before
	public void setUp() throws Exception {
		pkid = PrimaryKeyHelper.getCompKey();
		jdbc.update("insert into tb_book(id,bname) values('"+pkid+"','测试书名')");
	}

	@After
	public void tearDown() throws Exception {
		//jdbc.update("delete from tb_book where id"+pkid);
	}

	/**
	 * 测试单行数据读取,读取单条记录的方法都有一个小点需要注意“ 如果查不到数据时，会抛一个异常出来，所以需要针对这种场景进行额外处理 ”
	 */
	@Test
	public void testSingleRowQuery() {
		//1.queryForMap，一般用于查询单条数据，然后将db中查询的字段，填充到map中，key为列名，value为值
		String singleRowQuery = "select * from tb_book where id='"+pkid+"'";
		Map<String, Object> singleRowQueryMap = jdbc.queryForMap(singleRowQuery);
		assertNotNull(singleRowQueryMap);
		assertEquals(pkid, singleRowQueryMap.get("ID"));
		
		//查询不到时会提示 报错
		singleRowQuery = "select * from tb_book where id='xx'";
		singleRowQueryMap = null;
		boolean isEmpty = false;
		try {
			singleRowQueryMap = jdbc.queryForMap(singleRowQuery);
		} catch (EmptyResultDataAccessException e) {
			isEmpty = true;
		}
		assertTrue(isEmpty);
		
		//执行SQL注入的场景,加入注入SQL以后 会查询出所有的数据
		singleRowQuery = "select * from tb_book where id='"+pkid+"'";
		String condition = " or id<>'1' order by id";
		List<Map<String, Object>> singleRowQueryList = jdbc.queryForList(singleRowQuery+condition);
		assertNotNull(singleRowQueryList);
		assertTrue(singleRowQueryList.size()>1);
		
		//使用占位符替换查询  正是因为直接拼sql，可能导致sql注入的问题，所以更推荐的写法是通过占位符 + 传参的方式
		singleRowQuery = "select * from tb_book where id=?";
		//这里可以传入一个Object数组或者借助java不定长传参的方式 
		singleRowQueryMap = jdbc.queryForMap(singleRowQuery,new Object[] {pkid});
		//singleRowQueryMap = jdbc.queryForMap(singleRowQuery,pkid);
		assertNotNull(singleRowQueryMap);
		assertEquals(pkid, singleRowQueryMap.get("ID"));
		
		//2.

	}

	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
}
