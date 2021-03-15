package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.po.Article;

@Service
public interface ArticleService extends BaseService<Article, Integer> {
	List<Article> selectByKeywords(String keywords);

	int articleStateChanged(Integer id, Boolean isShow);

	List<Article> getByClassId(Integer id);

	int countByAll();

	int countByClassId(Integer id);
}
