package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.FavoriteMapperExtends;
import com.example.po.Favorite;
import com.example.po.FavoriteExample;
import com.example.po.FavoriteExample.Criteria;
import com.example.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	@Autowired
	private FavoriteMapperExtends favoriteMapper;

	@Override
	public List<Favorite> getFavoriteByUserId(Integer id) {
		FavoriteExample ex = new FavoriteExample();
		Criteria criteria = ex.createCriteria();
		criteria.andUserIdEqualTo(id);
		return favoriteMapper.selectByExample(ex);
	}

	@Override
	public Integer getFavoriteByObjId(Integer id, String type) {
		FavoriteExample ex = new FavoriteExample();
		Criteria criteria = ex.createCriteria();
		criteria.andObjIdEqualTo(id).andObjTypeEqualTo(type);
		return favoriteMapper.countByExample(ex);
	}

	@Override
	public Boolean checkedFavorite(Favorite record) {
		FavoriteExample ex = new FavoriteExample();
		Criteria criteria = ex.createCriteria();
		criteria.andUserIdEqualTo(record.getUserId()).andObjIdEqualTo(record.getObjId())
				.andObjTypeLike("%" + record.getObjType() + "%");
		List<Favorite> favorite = favoriteMapper.selectByExample(ex);
		System.out.println(favorite.size());
		if (!favorite.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public int add(Favorite record) {
		if (!checkedFavorite(record)) {
			return favoriteMapper.insertSelective(record);
		} else {
			FavoriteExample ex = new FavoriteExample();
			Criteria criteria = ex.createCriteria();
			criteria.andUserIdEqualTo(record.getUserId()).andObjIdEqualTo(record.getObjId())
					.andObjTypeEqualTo(record.getObjType());
			return favoriteMapper.deleteByExample(ex);
		}
	}

}
