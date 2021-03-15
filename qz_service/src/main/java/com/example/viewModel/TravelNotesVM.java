package com.example.viewModel;

import com.example.po.TravelNotes;

import lombok.Data;

@Data
public class TravelNotesVM extends BaseVM {
	private Integer id;

	private String title;

	private String time;

	private String imagePath;

	private String content;

	private String source;

	private Boolean isShow;

	private String imageKey;

	private Integer likeNum;

	public static TravelNotesVM from(TravelNotes travelNotes) {
		TravelNotesVM vm = modelMapper.map(travelNotes, TravelNotesVM.class);
		return vm;
	}
}
