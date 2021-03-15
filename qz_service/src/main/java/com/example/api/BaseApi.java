package com.example.api;

import org.modelmapper.ModelMapper;

import com.example.utils.ModelMapperSingle;

public class BaseApi {
	protected final static String DEFAULT_PAGE_SIZE = "10";
	protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

}
