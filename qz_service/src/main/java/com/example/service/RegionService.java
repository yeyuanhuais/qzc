package com.example.service;

import org.springframework.stereotype.Service;

import com.example.po.Region;

@Service
public interface RegionService extends BaseService<Region, Integer> {
	Integer countByAll();
}
