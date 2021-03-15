package com.example.viewModel;

import com.example.po.Classification;

import lombok.Data;

@Data
public class ClassificationVM extends BaseVM {
	private Integer id;

	private String className;

	public static ClassificationVM from(Classification classification) {
		ClassificationVM vm = modelMapper.map(classification, ClassificationVM.class);
		return vm;
	}
}
