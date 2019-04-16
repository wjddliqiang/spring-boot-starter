package com.lqq.bookbar.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jdbcTemplate")
public class JdbcTestController {
	private Logger logger = LoggerFactory.getLogger(JdbcTestController.class);
	//注入jdbc模板
	@Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 简单测试下JdbcTemplate
     *
     * @return
     */
    @RequestMapping("/simpleQuary")
    @ResponseBody
    public List<Object> simpleQuary() {
        logger.info("使用JdbcTemplate查询数据库");
        String sql = "SELECT * FROM t_weather_condition ";
        //List<Object> queryAllList = jdbcTemplate.query(sql, new Object[]{},
        //        new BeanPropertyRowMapper<>(TWeatherCondition.class));
        //logger.info("查询天气状况" + queryAllList);
        return null;
    }
}