package com.example.service;

import org.springframework.stereotype.Service;

import com.example.po.Classification;

@Service
public interface ClassificationService extends BaseService<Classification, Integer> {
	int countByAll();
}
