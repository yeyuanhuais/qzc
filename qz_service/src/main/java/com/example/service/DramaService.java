package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.po.Drama;

@Service
public interface DramaService extends BaseService<Drama, Integer> {
	List<String> getClassName();

	List<Drama> getDramaByClassName(String className);

	int countByAll();
}
