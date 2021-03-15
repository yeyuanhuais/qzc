package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.TravelNotesMapperExtends;
import com.example.po.TravelNotes;
import com.example.po.TravelNotesExample;
import com.example.po.TravelNotesExample.Criteria;
import com.example.service.TravelNotesService;

@Service
public class TravelNotesServiceImpl implements TravelNotesService {
	@Autowired
	private TravelNotesMapperExtends travelNotesMapper;

	@Override
	public List<TravelNotes> getAll() {
		// TODO Auto-generated method stub
		return travelNotesMapper.selectByExample(null);
	}

	@Override
	public int add(TravelNotes record) {
		// TODO Auto-generated method stub
		return travelNotesMapper.insertSelective(record);
	}

	@Override
	public int update(TravelNotes record) {
		// TODO Auto-generated method stub
		return travelNotesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return travelNotesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public TravelNotes getById(Integer id) {
		return travelNotesMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TravelNotes> selectByKeywords(String keywords) {
		TravelNotesExample ex = new TravelNotesExample();
		Criteria criteria = ex.createCriteria();
		criteria.andContentLike("%" + keywords + "%");
		List travelNotesList = travelNotesMapper.selectByExample(ex);
//		System.out.println(travelNotesList);
		return travelNotesList;
	}

	@Override
	public int travelNotesStateChanged(Integer id, Boolean isShow) {
		// TODO Auto-generated method stub
		TravelNotesExample ex = new TravelNotesExample();
		Criteria criteria = ex.createCriteria();
		criteria.andIdEqualTo(id);
		TravelNotes tn = new TravelNotes();
		tn.setIsShow(isShow);
		travelNotesMapper.updateByExampleSelective(tn, ex);
		return 1;
	}

	@Override
	public int count() {
		return travelNotesMapper.countByExample(null);

	}

}
