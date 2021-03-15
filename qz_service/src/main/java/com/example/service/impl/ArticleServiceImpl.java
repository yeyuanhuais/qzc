package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.ArticleMapperExtends;
import com.example.po.Article;
import com.example.po.ArticleExample;
import com.example.po.ArticleExample.Criteria;
import com.example.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleMapperExtends articleMapper;

	@Override
	public List<Article> getAll() {
		// TODO Auto-generated method stub
		ArticleExample ex = new ArticleExample();
		ex.setOrderByClause("`is_show` ASC, `time` DESC");
		return articleMapper.selectByExample(ex);
	}

	@Override
	public int add(Article record) {
		// TODO Auto-generated method stub
		return articleMapper.insertSelective(record);
	}

	@Override
	public int update(Article record) {
		// TODO Auto-generated method stub
		return articleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Article getById(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Article> selectByKeywords(String keywords) {
		ArticleExample ex = new ArticleExample();
		Criteria criteria = ex.createCriteria();
		criteria.andContentLike("%" + keywords + "%");
		List articleList = articleMapper.selectByExample(ex);
		return articleList;
	}

	@Override
	public int articleStateChanged(Integer id, Boolean isShow) {
		// TODO Auto-generated method stub
		ArticleExample ex = new ArticleExample();
		Criteria criteria = ex.createCriteria();
		criteria.andIdEqualTo(id);
		Article tn = new Article();
		tn.setIsShow(isShow);
		articleMapper.updateByExampleSelective(tn, ex);
		return 1;
	}

	@Override
	public List<Article> getByClassId(Integer id) {
		// TODO Auto-generated method stub
		ArticleExample ex = new ArticleExample();
		ex.setOrderByClause("`time` DESC");
		Criteria criteria = ex.createCriteria();
		criteria.andClassIdEqualTo(id);
		List articleList = articleMapper.selectByExample(ex);
		return articleList;
	}

	@Override
	public int countByAll() {
		return articleMapper.countByExample(null);
	}

	@Override
	public int countByClassId(Integer id) {
		ArticleExample ex = new ArticleExample();
		Criteria criteria = ex.createCriteria();
		criteria.andClassIdEqualTo(id);
		return articleMapper.countByExample(ex);
	}

}
