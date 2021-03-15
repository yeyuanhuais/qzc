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
import com.example.po.Navigation;
import com.example.service.NavigationService;
import com.example.viewModel.NavigationVM;
import com.example.viewModel.PageInfoVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/navigation")
@Api("导航栏管理")
public class NavigationApi extends BaseApi {
	@Autowired
	private NavigationService navigationService;

	@PostMapping("/getAll")
	@ApiOperation(value = "获取所有导航栏")
	@ApiImplicitParam(name = "queryInfo", value = "分页信息", required = true, paramType = "body", dataType = "PageInfoVM")
	public RestResponse<List<NavigationVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		if (queryInfo.getSize() == 0 || queryInfo.getNum() == 0) {
			if (queryInfo.getSize() == 0) {
				PageHelper.startPage(queryInfo.getNum(), navigationService.countByAll());
			} else if (queryInfo.getNum() == 0) {
				PageHelper.startPage(1, queryInfo.getSize());
			} else {
				PageHelper.startPage(1, navigationService.countByAll());
			}
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		List<Navigation> navigationList = navigationService.getAll();
		List<NavigationVM> navigationVM = navigationList.stream().map(d -> {
			NavigationVM vm = modelMapper.map(d, NavigationVM.class);
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(navigationVM, navigationService.countByAll());

	}

	@PostMapping("/add")
	@ApiOperation(value = "添加导航栏")
	@ApiImplicitParam(name = "model", value = "导航栏信息", required = true, paramType = "body", dataType = "Navigation")
	public RestResponse add(@RequestBody Navigation model) {
		navigationService.add(model);
		return RestResponse.ok();
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改导航栏")
	@ApiImplicitParam(name = "model", value = "导航栏信息", required = true, paramType = "body", dataType = "Navigation")
	public RestResponse edit(@RequestBody Navigation model) {
		navigationService.update(model);
		return RestResponse.ok();
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "删除导航栏")
	@ApiImplicitParam(name = "id", value = "导航栏id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse delete(@RequestParam("id") Integer id) {
		navigationService.delete(id);
		return RestResponse.ok();
	}

	@GetMapping("/getById")
	@ApiOperation(value = "根据id查询导航栏")
	@ApiImplicitParam(name = "id", value = "导航栏id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getById(@RequestParam("id") Integer id) {
		Navigation navigation = navigationService.getById(id);
		NavigationVM navigationVM = NavigationVM.from(navigation);
		return RestResponse.ok(navigationVM);
	}

	@PutMapping("{id}/navigationStateChanged/{isShow}")
	@ApiOperation(value = "根据id修改导航栏状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "导航栏id", required = true, paramType = "path", dataType = "Integer"),
			@ApiImplicitParam(name = "isShow", value = "导航栏状态", required = true, paramType = "path", dataType = "Boolean") })
	public RestResponse navigationStateChanged(@PathVariable("id") Integer id, @PathVariable("isShow") Boolean isShow) {
		navigationService.navigationStateChanged(id, isShow);
		return RestResponse.ok();
	}
}
