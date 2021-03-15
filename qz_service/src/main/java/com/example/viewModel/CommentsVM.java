package com.example.viewModel;

import java.util.Date;
import java.util.List;

import com.example.po.Comments;

import lombok.Data;

@Data
public class CommentsVM {
	private Integer id;

	private Integer pid;

	private String content;

	private Date createTime;

	private Integer likeNum;

	private Integer userId;

	private String userName;

	private Integer ownerId;

	private String ownerType;

	private List<Comments> commentsChildren;

}
