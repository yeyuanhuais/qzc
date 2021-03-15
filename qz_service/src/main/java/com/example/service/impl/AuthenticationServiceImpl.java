package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.config.property.SystemConfig;
import com.example.po.User;
import com.example.service.AuthenticationService;
import com.example.service.UserService;
import com.example.utils.RsaUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	@Autowired
	private UserService userService;

	private final SystemConfig systemConfig;

	@Override
	public boolean authUser(String userName, String password) {
		User user = userService.getUserByUserName(userName);
		return authUser(user, userName, password);
	}

	@Override
	public boolean authUser(User user, String userName, String password) {
		if (user == null) {
			return false;
		}
		String encodePwd = user.getPassword();
		if (null == encodePwd || encodePwd.length() == 0) {
			return false;
		}
		String pwd = pwdDecode(encodePwd);
		return pwd.equals(password);
	}

	@Override
	public String pwdEncode(String password) {
		return RsaUtil.rsaEncode(systemConfig.getPwdKey().getPublicKey(), password);
	}

	@Override
	public String pwdDecode(String encodePwd) {
		return RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(), encodePwd);
	}

}
