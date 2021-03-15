package com.example.viewModel;

import com.example.po.Article;

import lombok.Data;

@Data
public class ArticleVM extends BaseVM {
	private Integer id;

	private String title;

	private String time;

	private String content;

	private String source;

	private Boolean isShow;

	private Integer classId;

	private String className;

	private String imageKey;

	private String imagePath;

	private Integer likeNum;

	private Integer favoriteNum;

	public static ArticleVM from(Article article) {
		ArticleVM vm = modelMapper.map(article, ArticleVM.class);
		return vm;
	}
}
