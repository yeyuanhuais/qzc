package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.po.Favorite;

@Service
public interface FavoriteService {
	List<Favorite> getFavoriteByUserId(Integer id);

	Integer getFavoriteByObjId(Integer id, String type);

	Boolean checkedFavorite(Favorite record);

	int add(Favorite record);
}
