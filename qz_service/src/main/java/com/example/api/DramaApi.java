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
import com.example.po.Drama;
import com.example.service.DramaService;
import com.example.viewModel.DramaVM;
import com.example.viewModel.PageInfoVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/drama")
@Api("戏剧管理")
public class DramaApi extends BaseApi {
	@Autowired
	private DramaService dramaService;

	@GetMapping("/getClassName")
	@ApiOperation(value = "查询戏剧分类")
	public RestResponse getClassName() {
		return new RestResponse<>(1, "成功", dramaService.getClassName());
	}

	@GetMapping("/getDrama")
	@ApiOperation(value = "根据戏剧分类获取戏剧信息")
	@ApiImplicitParam(name = "className", value = "分类名", required = true, paramType = "query", dataType = "String")
	public RestResponse<List<DramaVM>> getDrama(@RequestParam("className") String className) {
		List<Drama> dramas = dramaService.getDramaByClassName(className);
		List<DramaVM> dramaVM = dramas.stream().map(d -> {
			DramaVM vm = modelMapper.map(d, DramaVM.class);
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(dramaVM);
	}

	@PostMapping("/getAll")
	@ApiOperation(value = "获取戏剧信息")
	@ApiImplicitParam(name = "queryInfo", value = "分页信息", required = true, paramType = "body", dataType = "PageInfoVM")
	public RestResponse<List<DramaVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		if (queryInfo.getSize() == 0 || queryInfo.getNum() == 0) {
			if (queryInfo.getSize() == 0) {
				PageHelper.startPage(queryInfo.getNum(), dramaService.countByAll());
			} else if (queryInfo.getNum() == 0) {
				PageHelper.startPage(1, queryInfo.getSize());
			} else {
				PageHelper.startPage(1, dramaService.countByAll());
			}
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		List<Drama> dramas = dramaService.getAll();
		List<DramaVM> dramaVM = dramas.stream().map(d -> {
			DramaVM vm = modelMapper.map(d, DramaVM.class);
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(dramaVM);

	}

	@PostMapping("/add")
	@ApiOperation(value = "添加戏剧视频")
	@ApiImplicitParam(name = "model", value = "戏剧视频信息", required = true, paramType = "body", dataType = "Drama")
	public RestResponse add(@RequestBody Drama model) {
		dramaService.add(model);
		return RestResponse.ok();
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改戏剧视频")
	@ApiImplicitParam(name = "model", value = "戏剧视频信息", required = true, paramType = "body", dataType = "Drama")
	public RestResponse edit(@RequestBody Drama model) {
		dramaService.update(model);
		return RestResponse.ok();
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "删除戏剧")
	@ApiImplicitParam(name = "id", value = "戏剧id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse delete(@RequestParam("id") Integer id) {
		dramaService.delete(id);
		return RestResponse.ok();
	}

	@GetMapping("/getById")
	@ApiOperation(value = "根据id查找戏剧")
	@ApiImplicitParam(name = "id", value = "戏剧id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getById(@RequestParam("id") Integer id) {
		Drama drama = dramaService.getById(id);
		DramaVM dramaVM = DramaVM.from(drama);
		return RestResponse.ok(dramaVM);
	}

}
