package com.example.api;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.base.RestResponse;
import com.example.po.Comments;
import com.example.service.CommentsService;
import com.example.viewModel.CommentsVM;
import com.example.viewModel.PageInfoVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
@Api("评论管理")
public class CommentsApi extends BaseApi {
	@Autowired
	private CommentsService commentsService;

	/**
	 * 添加评论 直接对标文章，资源等下面的评论
	 * 
	 * @param commentsRoot
	 * @return @
	 */
	@PostMapping("addRootComments")
	@ApiOperation(value = "添加评论 直接对标文章，资源等下面的评论")
	@ApiImplicitParam(name = "comments", value = "", required = true, paramType = "body", dataType = "Comments")
	public RestResponse addRootComments(@RequestBody Comments comments) {
		if (comments.getContent().length() != 0) {
			comments.setCreateTime(new Date());// 设置添加评论时间
			comments.setPid(0);
			commentsService.add(comments); // 调用service方法来完成评论的存储
			return RestResponse.ok();
		}
		// 评论内容为空 返回错误信息
		return RestResponse.fail(2, "评论内容不能为空");
	}

	/**
	 * 根据资源ID来获取该资源的所有评论
	 * 
	 * @param
	 * @return
	 */
	@GetMapping("getCommentsByOwnerId")
	@ApiOperation(value = "根据资源ID来获取该资源的所有评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "id", value = "", required = true, paramType = "query", dataType = "Integer") })

	public RestResponse<List<CommentsVM>> getListByOwnerId(@RequestParam("type") String type,
			@RequestParam("id") Integer id) {
		// 查询所有评论
		List<CommentsVM> commentsByOwnerIdList = commentsService.getCommentsByOwnerId(id, type);
		List<CommentsVM> commentsVM = commentsByOwnerIdList.stream().map(d -> {
			CommentsVM vm = modelMapper.map(d, CommentsVM.class);
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(commentsVM);
	}

	/**
	 * 根据用户ID来获取该用户的所有评论
	 * 
	 * @param
	 * @return
	 */
	@GetMapping("getCommentsByUserId")
	@ApiOperation(value = "根据用户ID来获取该用户的所有评论")
	@ApiImplicitParam(name = "userid", value = "", required = true, paramType = "query", dataType = "Integer")

	public RestResponse<List<Comments>> getCommentsByUserId(@RequestParam("userid") Integer userid) {
		// 查询所有评论
		List<Comments> commentsByUserIdList = commentsService.getCommentsByUserId(userid);
		return RestResponse.ok(commentsByUserIdList);
	}

	@DeleteMapping("delete")
	@ApiOperation(value = "用户删除评论")
	@ApiImplicitParam(name = "id", value = "", required = true, paramType = "query", dataType = "Integer")

	public RestResponse delete(@RequestParam("id") Integer id) {
		commentsService.delete(id);
		return RestResponse.ok();
	}

	@PostMapping("/getAll")
	@ApiOperation(value = "查询所有用户评论")
	public RestResponse<List<CommentsVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		if (queryInfo.getSize() == 0 || queryInfo.getNum() == 0) {
			if (queryInfo.getSize() == 0) {
				PageHelper.startPage(queryInfo.getNum(), commentsService.countByAll());
			} else if (queryInfo.getNum() == 0) {
				PageHelper.startPage(1, queryInfo.getSize());
			} else {
				PageHelper.startPage(1, commentsService.countByAll());
			}
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		String keywords = queryInfo.getKeyword();
		if (keywords.length() == 0 || keywords == null) {
			List<CommentsVM> vm = commentsService.getAllComments();
			return RestResponse.ok(vm, commentsService.countByAll());
		} else {
			List<CommentsVM> vm = commentsService.selectByKeywords(keywords);
			return RestResponse.ok(vm, commentsService.countByKeyWord(keywords));
		}
	}
}
