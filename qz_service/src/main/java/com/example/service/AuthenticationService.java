package com.example.service;

import org.springframework.stereotype.Service;

import com.example.po.User;

@Service
public interface AuthenticationService {

	/**
	 * authUser
	 *
	 * @param username userName
	 * @param password password
	 * @return boolean
	 */
	boolean authUser(String userName, String password);

	/**
	 * authUser
	 *
	 * @param user     user
	 * @param username userName
	 * @param password password
	 * @return boolean
	 */
	boolean authUser(User user, String userName, String password);

	/**
	 * pwdEncode
	 *
	 * @param password password
	 * @return String
	 */
	String pwdEncode(String password);

	/**
	 * pwdDecode
	 *
	 * @param endodePwd endodePwd
	 * @return String
	 */
	String pwdDecode(String endodePwd);

}
