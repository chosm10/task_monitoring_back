package com.rpa.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpa.domain.Bot;

@Repository
public interface BotRepository extends JpaRepository<Bot, Long>{
	
	public Page<Bot> findAll(Pageable pageable);
}
