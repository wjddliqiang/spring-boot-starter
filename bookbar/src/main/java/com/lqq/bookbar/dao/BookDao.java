package com.lqq.bookbar.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.lqq.bookbar.entity.Book;

@Repository
public class BookDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Book> findAll(){
		
		jdbcTemplate.query("select * from tb_book", new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				System.out.println(rs.getString("bname"));
				System.out.println(rs.getString("author"));
				
			}
		});
		
		return null;
	}

}
