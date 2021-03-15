package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.CommentsMapperExtends;
import com.example.po.Comments;
import com.example.po.CommentsExample;
import com.example.service.CommentsService;
import com.example.viewModel.CommentsVM;

@Service
public class CommentsServiceImpl implements CommentsService {
	@Autowired
	private CommentsMapperExtends commentsMapper;

	@Override
	public List<Comments> getAll() {
		return commentsMapper.selectByExample(null);
	}

	@Override
	public int add(Comments record) {
		// TODO Auto-generated method stub
		return commentsMapper.insertSelective(record);
	}

	@Override
	public int update(Comments record) {
		// TODO Auto-generated method stub
		return commentsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return commentsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Comments getById(Integer id) {
		// TODO Auto-generated method stub
		return commentsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CommentsVM> getAllComments() {
		List<Comments> firstComments = getCommentsByPid(0);
		List<CommentsVM> vms = new ArrayList<CommentsVM>();
		for (Comments comment : firstComments) {
			CommentsVM vm = new CommentsVM();
			List<Comments> childComments = getCommentsByPid(comment.getId());
			BeanUtils.copyProperties(comment, vm);
			vm.setCommentsChildren(childComments);
			vms.add(vm);
		}
		return vms;
	}

	@Override
	public List<Comments> getCommentsByPid(Integer pid) {
		CommentsExample ex = new CommentsExample();
		CommentsExample.Criteria criteria = ex.createCriteria();
		criteria.andPidEqualTo(pid);
		return commentsMapper.selectByExample(ex);
	}

	@Override
	public List<CommentsVM> getCommentsByOwnerId(Integer id, String type) {
		List<CommentsVM> commentsByOwnerId = getAllComments();
		List<CommentsVM> vms = new ArrayList<CommentsVM>();
		for (CommentsVM vm : commentsByOwnerId) {
			if (vm.getOwnerId() == id && vm.getOwnerType().equals(type)) {
				vms.add(vm);
			}
		}
		return vms;
	}

	@Override
	public List<Comments> getCommentsByUserId(Integer userid) {
		CommentsExample ex = new CommentsExample();
		CommentsExample.Criteria criteria = ex.createCriteria();
		criteria.andUserIdEqualTo(userid);
		return commentsMapper.selectByExample(ex);
	}

	@Override
	public int countByAll() {
		return commentsMapper.countByExample(null);
	}

	@Override
	public List<CommentsVM> selectByKeywords(String keywords) {
		List<CommentsVM> vm = getAllComments();
		List<CommentsVM> result = new ArrayList<CommentsVM>();
		for (CommentsVM first : vm) {
			if (StringUtils.containsIgnoreCase(first.getContent(), keywords)) {
				result.add(first);
			}
		}
		return result;
	}

	@Override
	public int countByKeyWord(String keywords) {
		// TODO Auto-generated method stub
		return selectByKeywords(keywords).size();
	}
}
