package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.RegionMapperExtends;
import com.example.po.Region;
import com.example.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionMapperExtends regionMapper;

	@Override
	public List<Region> getAll() {
		// TODO Auto-generated method stub
		return regionMapper.selectByExample(null);
	}

	@Override
	public int add(Region record) {
		// TODO Auto-generated method stub
		return regionMapper.insertSelective(record);
	}

	@Override
	public int update(Region record) {
		// TODO Auto-generated method stub
		return regionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return regionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Region getById(Integer id) {
		// TODO Auto-generated method stub
		return regionMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer countByAll() {
		// TODO Auto-generated method stub
		return regionMapper.countByExample(null);
	}

}
