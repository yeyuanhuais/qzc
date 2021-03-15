package com.example.viewModel;

import com.example.po.Drama;

import lombok.Data;

@Data
public class DramaVM extends BaseVM {
	private Integer id;

	private String title;

	private String path;

	private String dramaClass;

	public static DramaVM from(Drama drama) {
		DramaVM vm = modelMapper.map(drama, DramaVM.class);
		return vm;
	}

}
