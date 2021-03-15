package com.example.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.base.RestResponse;
import com.example.po.Classification;
import com.example.service.ClassificationService;
import com.example.viewModel.ClassificationVM;
import com.example.viewModel.PageInfoVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/classification")
@Api("分类管理")
public class ClassificationApi extends BaseApi {
	@Autowired
	private ClassificationService classificationService;

	@PostMapping("/getAll")
	@ApiOperation(value = "获取所有分类")
	@ApiImplicitParam(name = "model", value = "分页信息", required = true, paramType = "body", dataType = "User")
	public RestResponse<List<ClassificationVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		if (queryInfo.getSize() == 0 || queryInfo.getNum() == 0) {
			if (queryInfo.getSize() == 0) {
				PageHelper.startPage(queryInfo.getNum(), classificationService.countByAll());
			} else if (queryInfo.getNum() == 0) {
				PageHelper.startPage(1, queryInfo.getSize());
			} else {
				PageHelper.startPage(1, classificationService.countByAll());
			}
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		List<Classification> classificationList = classificationService.getAll();
		List<ClassificationVM> classificationVM = classificationList.stream().map(d -> {
			ClassificationVM vm = modelMapper.map(d, ClassificationVM.class);
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(classificationVM);

	}

	@PostMapping("/add")
	@ApiOperation(value = "添加分类")
	@ApiImplicitParam(name = "model", value = "分类信息", required = true, paramType = "body", dataType = "Classification")
	public RestResponse add(@RequestBody Classification model) {
		classificationService.add(model);
		return RestResponse.ok();
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改分类")
	@ApiImplicitParam(name = "model", value = "分类信息", required = true, paramType = "body", dataType = "Classification")
	public RestResponse edit(@RequestBody Classification model) {
		classificationService.update(model);
		return RestResponse.ok();
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "删除分类")
	@ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse delete(@RequestParam("id") Integer id) {
		classificationService.delete(id);
		return RestResponse.ok();
	}

	@GetMapping(value = "/getById")
	@ApiOperation(value = "根据id查询分类")
	@ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getById(@RequestParam("id") Integer id) {
		Classification classification = classificationService.getById(id);
		ClassificationVM classificationVM = ClassificationVM.from(classification);
		return RestResponse.ok(classificationVM);
	}
}
