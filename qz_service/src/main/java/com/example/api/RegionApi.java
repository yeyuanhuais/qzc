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
import com.example.po.Region;
import com.example.service.RegionService;
import com.example.utils.TencentCOS;
import com.example.viewModel.PageInfoVM;
import com.example.viewModel.RegionVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/region")
@Api("景点")
public class RegionApi extends BaseApi {
	@Autowired
	private RegionService regionService;

	@PostMapping("/getAll")
	@ApiOperation(value = "获取所有景点")
	@ApiImplicitParam(name = "queryInfo", value = "分页信息", required = true, paramType = "body", dataType = "PageInfoVM")
	public RestResponse<List<RegionVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		if (queryInfo.getSize() == 0 || queryInfo.getNum() == 0) {
			if (queryInfo.getSize() == 0) {
				PageHelper.startPage(queryInfo.getNum(), regionService.countByAll());
			} else if (queryInfo.getNum() == 0) {
				PageHelper.startPage(1, queryInfo.getSize());
			} else {
				PageHelper.startPage(1, regionService.countByAll());
			}
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		List<Region> regionList = regionService.getAll();
		List<RegionVM> regionVM = regionList.stream().map(d -> {
			RegionVM vm = modelMapper.map(d, RegionVM.class);
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(regionVM, regionService.countByAll());

	}

	@PostMapping("/add")
	@ApiOperation(value = "添加景点")
	@ApiImplicitParam(name = "model", value = "景点信息", required = true, paramType = "body", dataType = "Region")
	public RestResponse add(@RequestBody Region model) {
		regionService.add(model);
		return RestResponse.ok();
	}

	@GetMapping("/getById")
	@ApiOperation(value = "根据id查询景点")
	@ApiImplicitParam(name = "id", value = "景点id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getById(@RequestParam("id") Integer id) {
		Region region = regionService.getById(id);
		RegionVM regionVM = RegionVM.from(region);
		return RestResponse.ok(regionVM);
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改景点")
	@ApiImplicitParam(name = "model", value = "景点信息", required = true, paramType = "body", dataType = "Region")
	public RestResponse edit(@RequestBody Region model) {
		regionService.update(model);
		return RestResponse.ok();
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "删除景点")
	@ApiImplicitParam(name = "id", value = "景点id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse delete(@RequestParam("id") Integer id) {
		Region tn = regionService.getById(id);
		if (tn.getImageKey().length() != 0) {
			TencentCOS.deletefile(tn.getImageKey());
			regionService.delete(id);
			return RestResponse.ok();
		}
		regionService.delete(id);
		return RestResponse.ok();
	}
}
