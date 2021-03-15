package com.example.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.po.TravelNotes;
import com.example.service.LikedService;
import com.example.service.TravelNotesService;
import com.example.utils.TencentCOS;
import com.example.viewModel.PageInfoVM;
import com.example.viewModel.TravelNotesVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/travelNotes")
@AllArgsConstructor
@CrossOrigin
@Api("游记管理")
public class TravelNotesApi extends BaseApi {
	@Autowired
	private TravelNotesService travelNotesService;
	@Autowired
	private LikedService likedService;

	@PostMapping(value = "/getAll")
	@ApiOperation(value = "获取所有游记")
	@ApiImplicitParam(name = "queryInfo", value = "游记信息", required = true, paramType = "body", dataType = "PageInfoVM")
	public RestResponse<List<TravelNotesVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		if (queryInfo.getSize() == 0 || queryInfo.getNum() == 0) {
			if (queryInfo.getSize() == 0) {
				PageHelper.startPage(queryInfo.getNum(), travelNotesService.count());
			} else if (queryInfo.getNum() == 0) {
				PageHelper.startPage(1, queryInfo.getSize());
			} else {
				PageHelper.startPage(1, travelNotesService.count());
			}
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		String keywords = queryInfo.getKeyword();
		if (keywords.length() == 0 || keywords == null) {
			List<TravelNotes> travelNotesList = travelNotesService.getAll();
			List<TravelNotesVM> travelNotesVM = travelNotesList.stream().map(d -> {
				d.setLikeNum(likedService.getLikedByObjId(d.getId(), "游记"));
				TravelNotesVM vm = modelMapper.map(d, TravelNotesVM.class);
				return vm;
			}).collect(Collectors.toList());
			return RestResponse.ok(travelNotesVM, travelNotesService.count());
		} else {
			List<TravelNotes> travelNotesList = travelNotesService.selectByKeywords(keywords);
			List<TravelNotesVM> travelNotesVM = travelNotesList.stream().map(d -> {
				TravelNotesVM vm = modelMapper.map(d, TravelNotesVM.class);
				return vm;
			}).collect(Collectors.toList());
			return RestResponse.ok(travelNotesVM, travelNotesService.count());
		}
//		return RestResponse.ok();
	}

	@PostMapping(value = "/add")
	@ApiOperation(value = "添加游记")
	@ApiImplicitParam(name = "model", value = "游记信息", required = true, paramType = "body", dataType = "TravelNotes")
	public RestResponse add(@RequestBody TravelNotes model) {
		travelNotesService.add(model);
		return RestResponse.ok();
	}

	@PutMapping(value = "/edit")
	@ApiOperation(value = "修改游记")
	@ApiImplicitParam(name = "model", value = "分类游记", required = true, paramType = "body", dataType = "TravelNotes")
	public RestResponse edit(@RequestBody TravelNotes model) {
		travelNotesService.update(model);
		return RestResponse.ok();
	}

	@DeleteMapping(value = "/delete")
	@ApiOperation(value = "删除游记")
	@ApiImplicitParam(name = "id", value = "游记id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse delete(@RequestParam("id") Integer id) {
		System.out.println(id);
		TravelNotes tn = travelNotesService.getById(id);
		if (tn.getImageKey().length() != 0) {
			TencentCOS.deletefile(tn.getImageKey());
			travelNotesService.delete(id);
			return RestResponse.ok();
		}
		travelNotesService.delete(id);
		return RestResponse.ok();
	}

	@GetMapping(value = "/getById")
	@ApiOperation(value = "根据id查询游记")
	@ApiImplicitParam(name = "id", value = "游记id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse getById(@RequestParam("id") Integer id) {
		TravelNotes travelNote = travelNotesService.getById(id);
		TravelNotesVM travelNotesVM = TravelNotesVM.from(travelNote);
		return RestResponse.ok(travelNotesVM);
	}

	@PutMapping("{id}/travelNotesStateChanged/{isShow}")
	@ApiOperation(value = "根据id修改游记状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "游记id", required = true, paramType = "path", dataType = "Integer"),
			@ApiImplicitParam(name = "isShow", value = "游记状态", required = true, paramType = "path", dataType = "Boolean") })
	public RestResponse travelNotesStateChanged(@PathVariable("id") Integer id,
			@PathVariable("isShow") Boolean isShow) {
		System.out.println(id);
		System.out.println(isShow);
		travelNotesService.travelNotesStateChanged(id, isShow);
		return RestResponse.ok();
	}

}
