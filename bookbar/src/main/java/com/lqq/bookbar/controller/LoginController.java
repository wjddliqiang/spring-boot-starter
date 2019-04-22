/**
 * 
 */
package com.lqq.bookbar.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.support.logging.LogFactory;
import com.lqq.bookbar.entity.User;

/**
 * @author LQQ
 *
 */
@Transactional
@Controller
public class LoginController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/login")
	public String login(@RequestParam String uid,@RequestParam String pwd) {
		
		String sqlString = "select * from tb_user where userid =? and pwd=?";
		RowMapper<User> rowMapper =new BeanPropertyRowMapper<User>(User.class);
		//queryForObject  此方法只能返回单条数据，如果查询到0条或多条数据就会报错  Incorrect result size
		//使用此方法需要实现User类使用接口 RowMapper<User>
		//写法1 User实现接口rowMapper
		User user = jdbcTemplate.queryForObject(sqlString, rowMapper, new Object[]{uid,pwd});
		//写法2 使用匿名内部类 RowMapper
		User user1 = jdbcTemplate.queryForObject(sqlString, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				if (rs != null) {
					user.setId(rs.getString("id"));
					user.setUserid(rs.getString("userid"));
					user.setPwd(rs.getString("pwd"));
				}
				return user;
			}
			
			
		},new Object[] {uid,pwd});
		//queryForList    使用此方法取多条记录
		
		
		
		
		LoggerFactory.getLogger(this.getClass()).info("执行数据："+user);
		
		return "index";
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}
