package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.DramaMapperExtends;
import com.example.po.Drama;
import com.example.po.DramaExample;
import com.example.service.DramaService;

@Service
public class DramsServiceImpl implements DramaService {
	@Autowired
	private DramaMapperExtends dramaMapper;

	@Override
	public List<Drama> getAll() {
		// TODO Auto-generated method stub
		return dramaMapper.selectByExample(null);
	}

	@Override
	public int add(Drama record) {
		// TODO Auto-generated method stub
		return dramaMapper.insertSelective(record);
	}

	@Override
	public int update(Drama record) {
		// TODO Auto-generated method stub
		return dramaMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return dramaMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Drama getById(Integer id) {
		// TODO Auto-generated method stub
		return dramaMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<String> getClassName() {
		List<Drama> dramas = getAll();
		List<String> className = new ArrayList<String>();
		for (Drama one : dramas) {
			if (!className.contains(one.getDramaClass())) {
				className.add(one.getDramaClass());
			}
		}
		return className;
	}

	@Override
	public List<Drama> getDramaByClassName(String className) {
		// TODO Auto-generated method stub
		DramaExample ex = new DramaExample();
		ex.createCriteria().andDramaClassEqualTo(className);
		return dramaMapper.selectByExample(ex);
	}

	@Override
	public int countByAll() {
		// TODO Auto-generated method stub
		return dramaMapper.countByExample(null);
	}

}
