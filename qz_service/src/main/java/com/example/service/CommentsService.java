package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.po.Comments;
import com.example.viewModel.CommentsVM;

@Service
public interface CommentsService extends BaseService<Comments, Integer> {
	List<Comments> getCommentsByPid(Integer pid);

	List<CommentsVM> getAllComments();

	List<CommentsVM> getCommentsByOwnerId(Integer id, String type);

	List<Comments> getCommentsByUserId(Integer userid);

	int countByAll();

	int countByKeyWord(String keywords);

	List<CommentsVM> selectByKeywords(String keywords);
}
