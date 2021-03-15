package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.ClassificationMapperExtends;
import com.example.po.Classification;
import com.example.service.ClassificationService;

@Service
public class ClassificationServiceImpl implements ClassificationService {
	@Autowired
	private ClassificationMapperExtends classificationMapper;

	@Override
	public List<Classification> getAll() {
		// TODO Auto-generated method stub
		return classificationMapper.selectByExample(null);
	}

	@Override
	public int add(Classification record) {
		// TODO Auto-generated method stub
		return classificationMapper.insertSelective(record);
	}

	@Override
	public int update(Classification record) {
		// TODO Auto-generated method stub
		return classificationMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return classificationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Classification getById(Integer id) {
		// TODO Auto-generated method stub
		return classificationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int countByAll() {
		return classificationMapper.countByExample(null);

	}

}
