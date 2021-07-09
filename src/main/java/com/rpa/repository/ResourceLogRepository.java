package com.rpa.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpa.domain.ResourceLog;

@Repository
public interface ResourceLogRepository extends JpaRepository<ResourceLog, Long>{
	public Page<ResourceLog> findAllByTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
	
	public Page<ResourceLog> findAllByIpAndTimeBetween(String ip, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
