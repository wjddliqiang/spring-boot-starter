package com.lqq.bookbar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lqq.bookbar.entity.User;
import com.lqq.bookbar.service.RedisService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/saveUser")
    public String saveUser(Long id, String userName, String  passWord) {
        User user = new User();
        user.setUname(userName);
        user.setPwd(passWord);
        user.setId(String.valueOf(id));
        if (redisService.isConnOk()) {
        	redisService.set(String.valueOf(id), user);
		}
        return "success";
    }

    @RequestMapping("/getUserById")
    public User getUserById(Long id) {
    	User res = null;
    	if (redisService.isConnOk()) {
    		res = (User) redisService.get(String.valueOf(id));
            System.out.println(res);
	}
        
        return res;
    }
}