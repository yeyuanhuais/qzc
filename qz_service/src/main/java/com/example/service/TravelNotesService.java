package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.po.TravelNotes;

@Service
public interface TravelNotesService extends BaseService<TravelNotes, Integer> {
	List<TravelNotes> selectByKeywords(String keywords);

	int travelNotesStateChanged(Integer id, Boolean isShow);

	int count();
}
