package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.po.Liked;

@Service
public interface LikedService {
	List<Liked> getLikedByUserId(Integer id);

	Integer getLikedByObjId(Integer id, String type);

	Boolean checkedLike(Liked record);

	int add(Liked record);

	int delete(Liked record);
}
