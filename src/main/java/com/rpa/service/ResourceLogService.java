package com.rpa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rpa.domain.ResourceLog;
import com.rpa.repository.ResourceLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResourceLogService {
	
	private final ResourceLogRepository resourceLogRepository;
	
//	업무 추가
	@Transactional // 변경 시 필요
	public void save(ResourceLog log) {
		
		resourceLogRepository.save(log).getId();
	}
	
	@Transactional // 변경 시 필요
	public void save(List<ResourceLog> logs) {
		
		for(ResourceLog log: logs) {
			resourceLogRepository.save(log);
		}
		
	}
	
	//전체 업무 조회
	public List<ResourceLog> findAllResourceLogs() {
		
		return resourceLogRepository.findAll();
	}
	
}
