package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.LikedMapperExtends;
import com.example.po.Liked;
import com.example.po.LikedExample;
import com.example.po.LikedExample.Criteria;
import com.example.service.LikedService;

@Service
public class LikedServiceImpl implements LikedService {
	@Autowired
	private LikedMapperExtends likedMapper;

	/**
	 * 点赞
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public int add(Liked record) {
		// TODO Auto-generated method stub
		if (!checkedLike(record)) {
			return likedMapper.insertSelective(record);
		} else {
			LikedExample ex = new LikedExample();
			Criteria criteria = ex.createCriteria();
			criteria.andUserIdEqualTo(record.getUserId()).andObjIdEqualTo(record.getObjId())
					.andObjTypeEqualTo(record.getObjType());
			return likedMapper.deleteByExample(ex);
		}
	}

	/**
	 * 点赞或取消点赞
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int delete(Liked record) {
		// TODO Auto-generated method stub
		return 1;
	}

	/**
	 * 查询单个用户的所有点赞信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Liked> getLikedByUserId(Integer id) {
		LikedExample ex = new LikedExample();
		Criteria criteria = ex.createCriteria();
		criteria.andUserIdEqualTo(id).andLikeStatusEqualTo(true);
		return likedMapper.selectByExample(ex);
	}

	/**
	 * 检测用户是否点赞了
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public Boolean checkedLike(Liked record) {
		LikedExample ex = new LikedExample();
		Criteria criteria = ex.createCriteria();
		criteria.andUserIdEqualTo(record.getUserId()).andObjIdEqualTo(record.getObjId())
				.andObjTypeLike("%" + record.getObjType() + "%");
		List<Liked> liked = likedMapper.selectByExample(ex);
		if (!liked.isEmpty() && liked.get(0).getLikeStatus() == true) {
			return true;
		}
		return false;
	}

	/* 获取该对象的点赞 */
	@Override
	public Integer getLikedByObjId(Integer id, String type) {
		LikedExample ex = new LikedExample();
		Criteria criteria = ex.createCriteria();
		criteria.andObjIdEqualTo(id).andObjTypeEqualTo(type).andLikeStatusEqualTo(true);
		return likedMapper.countByExample(ex);
	}

}
