package com.example.viewModel;

import org.modelmapper.ModelMapper;

import com.example.utils.ModelMapperSingle;

import lombok.Data;

@Data
public class BaseVM {
	protected static ModelMapper modelMapper = ModelMapperSingle.Instance();

}
