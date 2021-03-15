package com.example.viewModel;

import java.util.Date;

import com.example.po.Region;

import lombok.Data;

@Data
public class RegionVM extends BaseVM {
	private Integer id;

	private String title;

	private String content;

	private String digest;

	private Date createTime;

	private String author;

	private String source;

	private String imageKey;

	private String imagePath;

	public static RegionVM from(Region region) {
		RegionVM vm = modelMapper.map(region, RegionVM.class);
		return vm;
	}
}
