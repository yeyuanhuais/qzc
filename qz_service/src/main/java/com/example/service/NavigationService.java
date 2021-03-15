package com.example.service;

import org.springframework.stereotype.Service;

import com.example.po.Navigation;

@Service
public interface NavigationService extends BaseService<Navigation, Integer> {

	int navigationStateChanged(Integer id, Boolean isShow);

	int countByAll();
}
