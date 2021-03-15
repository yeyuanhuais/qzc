package com.example.service;

import org.springframework.stereotype.Service;

import com.example.po.User;
import com.example.viewModel.UserLoginVM;

@Service
public interface UserService extends BaseService<User, Integer> {
	User getUserByUserName(String userName);

	void sendMimeMail(String to, String code);

	// 用户激活
	int setUserEnable(User user, String code);

	// 用户登录检查
	int checkLogin(UserLoginVM user);

	int count();

	int userStateChanged(Integer id, Boolean isShow);

	int userRoleChanged(Integer id, Boolean isShow);
}
