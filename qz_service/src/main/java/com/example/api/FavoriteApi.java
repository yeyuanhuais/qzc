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
import com.example.po.Favorite;
import com.example.po.TravelNotes;
import com.example.service.ArticleService;
import com.example.service.FavoriteService;
import com.example.service.TravelNotesService;
import com.example.viewModel.FavoriteVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/favorite")
@Api("收藏")
public class FavoriteApi {
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private TravelNotesService travelNotesService;

	@PostMapping("/add")
	@ApiOperation(value = "添加收藏")
	@ApiImplicitParam(name = "favorite", value = "收藏信息", required = true, paramType = "body", dataType = "Favorite")
	public RestResponse add(@RequestBody Favorite favorite) {
		favoriteService.add(favorite);
		return RestResponse.ok();
	}

	@GetMapping("/user")
	@ApiOperation(value = "根据用户查找收藏")
	@ApiImplicitParam(name = "userId", value = "收藏信息", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getUserLike(@RequestParam("userId") Integer id) {
		List<Favorite> favorites = favoriteService.getFavoriteByUserId(id);
		List<TravelNotes> travelNotes = new ArrayList<TravelNotes>();
		List<Article> articles = new ArrayList<Article>();
		for (Favorite one : favorites) {
			if (one.getObjType().equals("游记")) {
				TravelNotes travelNote = travelNotesService.getById(one.getObjId());
				travelNotes.add(travelNote);
			} else {
				Article article = articleService.getById(one.getObjId());
				articles.add(article);
			}
		}
		List favoriteList = null;
		favoriteList.add(articles);
		favoriteList.add(travelNotes);
		return new RestResponse<>(1, "成功", favoriteList);
	}

	@GetMapping("/obj")
	@ApiOperation(value = "根据类型查找收藏")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "objId", value = "被收藏id", required = true, paramType = "query", dataType = "Integer"),
			@ApiImplicitParam(name = "objType", value = "被收藏类型", required = true, paramType = "query", dataType = "String") })
	public RestResponse<FavoriteVM> getObjLike(@RequestParam("objId") Integer id,
			@RequestParam("objType") String type) {
		FavoriteVM vm = new FavoriteVM();
		vm.setNumber(favoriteService.getFavoriteByObjId(id, type));
		return RestResponse.ok(vm);
	}

	@PostMapping("/checkFavorite")
	@ApiOperation(value = "检查收藏状态")
	@ApiImplicitParam(name = "favorite", value = "收藏信息", required = true, paramType = "body", dataType = "Favorite")
	public RestResponse<FavoriteVM> checkLike(@RequestBody Favorite favorite) {
		FavoriteVM vm = new FavoriteVM();
		vm.setFavorite(favoriteService.checkedFavorite(favorite));
		return RestResponse.ok(vm);
	}
}
