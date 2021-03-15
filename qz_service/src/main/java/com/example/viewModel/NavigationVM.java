package com.example.viewModel;

import com.example.po.Navigation;

import lombok.Data;

@Data
public class NavigationVM extends BaseVM {
	private Integer id;

	private String menuName;

	private String icon;

	private String url;

	private Boolean isShow;

	private Integer sort;

	public static NavigationVM from(Navigation navigation) {
		NavigationVM vm = modelMapper.map(navigation, NavigationVM.class);
		return vm;
	}
}
