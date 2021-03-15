package com.example.viewModel;

import java.text.ParseException;

import com.example.po.User;
import com.example.utils.DateTimeUtil;

import lombok.Data;

@Data
public class UserResponseVM extends BaseVM {

	private Integer id;

	private String userName;

	private String password;

	private String email;

	private String code;

	private String imageKey;

	private String imagePath;

	private String token;

	private Boolean role;

	private Boolean status;

	private String createTime;

	public static UserResponseVM from(User user) {
		UserResponseVM vm = modelMapper.map(user, UserResponseVM.class);
		try {
			vm.setCreateTime(DateTimeUtil.dateFormat(user.getCreateTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vm;
	}

}
