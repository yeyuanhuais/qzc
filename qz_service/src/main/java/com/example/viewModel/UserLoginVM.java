package com.example.viewModel;

import com.example.po.User;

import lombok.Data;

@Data
public class UserLoginVM extends BaseVM {
	private String userName;
	private String password;
	private String captcha;

	public static UserLoginVM from(User user) {
		UserLoginVM vm = modelMapper.map(user, UserLoginVM.class);
		return vm;
	}
}
