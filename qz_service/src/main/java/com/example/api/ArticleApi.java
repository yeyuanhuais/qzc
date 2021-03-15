package com.example.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.base.RestResponse;
import com.example.config.interceptor.UserLoginToken;
import com.example.po.Article;
import com.example.service.ArticleService;
import com.example.service.FavoriteService;
import com.example.service.LikedService;
import com.example.utils.TencentCOS;
import com.example.viewModel.ArticleVM;
import com.example.viewModel.PageInfoVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/article")
@Api("文章管理")
public class ArticleApi extends BaseApi {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private LikedService likedService;
	@Autowired
	private FavoriteService favoriteService;

	@PostMapping("/getAll")
	@ApiOperation(value = "获取所有文章")
	@ApiImplicitParam(name = "queryInfo", value = "分页信息", required = true, paramType = "body", dataType = "PageInfoVM")
	public RestResponse<List<ArticleVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		String keywords = queryInfo.getKeyword();
		if (queryInfo.getSize() == 0 || queryInfo.getNum() == 0) {
			if (queryInfo.getSize() == 0) {
				PageHelper.startPage(queryInfo.getNum(), articleService.countByAll());
			} else if (queryInfo.getNum() == 0) {
				PageHelper.startPage(1, queryInfo.getSize());
			} else {
				PageHelper.startPage(1, articleService.countByAll());
			}
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		if ((keywords.length() == 0 || keywords == null) && queryInfo.getClassId() == 0) {
			List<Article> articleList = articleService.getAll();
			List<ArticleVM> articleVM = articleList.stream().map(d -> {
				ArticleVM vm = modelMapper.map(d, ArticleVM.class);
				return vm;
			}).collect(Collectors.toList());
			return RestResponse.ok(articleVM, articleService.countByAll());
		} else if (queryInfo.getClassId() != 0) {
			List<Article> articleList = articleService.getByClassId(queryInfo.getClassId());
			List<ArticleVM> articleVM = articleList.stream().map(d -> {
				ArticleVM vm = modelMapper.map(d, ArticleVM.class);
				return vm;
			}).collect(Collectors.toList());
			return RestResponse.ok(articleVM, articleService.countByClassId(queryInfo.getClassId()));
		} else {
			List<Article> articleList = articleService.selectByKeywords(keywords);
			List<ArticleVM> articleVM = articleList.stream().map(d -> {
				ArticleVM vm = modelMapper.map(d, ArticleVM.class);
				return vm;
			}).collect(Collectors.toList());
			return RestResponse.ok(articleVM, articleService.countByAll());
		}

	}

	@PostMapping("/add")
	@ApiOperation(value = "添加文章")
	@ApiImplicitParam(name = "model", value = "文章信息", required = true, paramType = "body", dataType = "Article")
	public RestResponse add(@RequestBody Article model) {
		articleService.add(model);
		return RestResponse.ok();
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改文章")
	@ApiImplicitParam(name = "model", value = "分类文章", required = true, paramType = "body", dataType = "Article")
	public RestResponse edit(@RequestBody Article model) {
		articleService.update(model);
		return RestResponse.ok();
	}

	@UserLoginToken
	@DeleteMapping("/delete")
	@ApiOperation(value = "删除文章")
	@ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse delete(@RequestParam("id") Integer id) {
		Article tn = articleService.getById(id);
		if (tn.getImageKey().length() != 0) {
			TencentCOS.deletefile(tn.getImageKey());
			articleService.delete(id);
			return RestResponse.ok();
		}
		articleService.delete(id);
		return RestResponse.ok();
	}

	@GetMapping("/getById")
	@ApiOperation(value = "根据id查询文章")
	@ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getById(@RequestParam("id") Integer id) {
		Article article = articleService.getById(id);
		String className = article.getClassName();
		Integer likeNumber = likedService.getLikedByObjId(id, className);
		Integer favoriteNum = favoriteService.getFavoriteByObjId(id, className);
		article.setLikeNum(likeNumber);
		article.setFavoriteNum(favoriteNum);
		articleService.update(article);
		ArticleVM articleVM = ArticleVM.from(article);
		return RestResponse.ok(articleVM);
	}

	@UserLoginToken
	@PutMapping("{id}/articleStateChanged/{isShow}")
	@ApiOperation(value = "根据id修改文章状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "Integer"),
			@ApiImplicitParam(name = "isShow", value = "文章状态", required = true, paramType = "path", dataType = "Boolean") })
	public RestResponse articleStateChanged(@PathVariable("id") Integer id, @PathVariable("isShow") Boolean isShow) {
		articleService.articleStateChanged(id, isShow);
		return RestResponse.ok();
	}

	@GetMapping("/getByClassId")
	@ApiOperation(value = "根据id查询分类")
	@ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse<List<ArticleVM>> getByClassId(@RequestParam("classId") Integer id,
			@RequestParam("size") Integer size, @RequestParam("num") Integer num) {
		if (size == 0 || num == 0) {
			if (size == 0) {
				PageHelper.startPage(num, articleService.countByClassId(id));
			} else if (num == 0) {
				PageHelper.startPage(1, size);
			} else {
				PageHelper.startPage(1, articleService.countByClassId(id));
			}
		} else {
			PageHelper.startPage(num, size);
		}
		List<Article> articleList = articleService.getByClassId(id);
		if (articleList.size() == 0) {
			return RestResponse.fail(2, "不存在该分类");
		}
		List<ArticleVM> articleVM = articleList.stream().map(d -> {
			ArticleVM vm = modelMapper.map(d, ArticleVM.class);
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(articleVM, articleService.countByClassId(id));

	}
}
