package com.example.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.base.RestResponse;
import com.example.po.Article;
import com.example.po.Liked;
import com.example.po.TravelNotes;
import com.example.service.ArticleService;
import com.example.service.LikedService;
import com.example.service.TravelNotesService;
import com.example.viewModel.LikeVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/like")
@Api("点赞")
public class LikeApi {
	@Autowired
	private LikedService likeService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private TravelNotesService travelNotesService;

	@PostMapping("/add")
	@ApiOperation(value = "添加点赞")
	@ApiImplicitParam(name = "liked", value = "点赞信息", required = true, paramType = "body", dataType = "Liked")
	public RestResponse add(@RequestBody Liked liked) {
		liked.setLikeStatus(true);
		likeService.add(liked);
		return RestResponse.ok();
	}

	@GetMapping("/user")
	@ApiOperation(value = "根据用户查找点赞")
	@ApiImplicitParam(name = "userId", value = "点赞信息", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getUserLike(@RequestParam("userId") Integer id) {
		List<Liked> liked = likeService.getLikedByUserId(id);
		List<TravelNotes> travelNotes = new ArrayList<TravelNotes>();
		List<Article> articles = new ArrayList<Article>();
		for (Liked one : liked) {
			if (one.getObjType().equals("游记")) {
				TravelNotes travelNote = travelNotesService.getById(one.getObjId());
				travelNotes.add(travelNote);
			} else {
				Article article = articleService.getById(one.getObjId());
				articles.add(article);
			}
		}
		List list = null;
		list.add(articles);
		list.add(travelNotes);
		return new RestResponse<>(1, "成功", list);
	}

	@GetMapping("/obj")
	@ApiOperation(value = "根据类型查找点赞")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "objId", value = "被点赞id", required = true, paramType = "query", dataType = "Integer"),
			@ApiImplicitParam(name = "objType", value = "被点赞类型", required = true, paramType = "query", dataType = "String") })
	public RestResponse<LikeVM> getObjLike(@RequestParam("objId") Integer id, @RequestParam("objType") String type) {
		LikeVM vm = new LikeVM();
		vm.setNumber(likeService.getLikedByObjId(id, type));
		return RestResponse.ok(vm);
	}

	@PostMapping("/checkLike")
	@ApiOperation(value = "检查点赞状态")
	@ApiImplicitParam(name = "liked", value = "点赞信息", required = true, paramType = "body", dataType = "Liked")
	public RestResponse<LikeVM> checkLike(@RequestBody Liked liked) {
		LikeVM vm = new LikeVM();
		vm.setLike(likeService.checkedLike(liked));
		return RestResponse.ok(vm);
	}
}
