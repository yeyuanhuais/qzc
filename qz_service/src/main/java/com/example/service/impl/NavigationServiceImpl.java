package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.NavigationMapperExtends;
import com.example.po.Navigation;
import com.example.po.NavigationExample;
import com.example.po.NavigationExample.Criteria;
import com.example.service.NavigationService;

@Service
public class NavigationServiceImpl implements NavigationService {
	@Autowired
	private NavigationMapperExtends navigationMapper;

	@Override
	public List<Navigation> getAll() {
		// TODO Auto-generated method stub
		return navigationMapper.selectByExample(null);
	}

	@Override
	public int add(Navigation record) {
		// TODO Auto-generated method stub
		return navigationMapper.insertSelective(record);
	}

	@Override
	public int update(Navigation record) {
		// TODO Auto-generated method stub
		return navigationMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return navigationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Navigation getById(Integer id) {
		// TODO Auto-generated method stub
		return navigationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int navigationStateChanged(Integer id, Boolean isShow) {
		// TODO Auto-generated method stub
		NavigationExample ex = new NavigationExample();
		Criteria criteria = ex.createCriteria();
		criteria.andIdEqualTo(id);
		Navigation tn = new Navigation();
		tn.setIsShow(isShow);
		navigationMapper.updateByExampleSelective(tn, ex);
		return 1;
	}

	@Override
	public int countByAll() {
		return navigationMapper.countByExample(null);

	}

}
