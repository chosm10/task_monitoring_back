package com.rpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rpa.domain.Bot;
import com.rpa.repository.BotRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BotService {
	
	private final BotRepository botRepository;
//	업무 추가
	@Transactional // 변경 시 필요
	public Long save(Bot bot) {
		return botRepository.save(bot).getId();
	}
	
	public Page<Bot> findAll(Pageable pageable) {
		return botRepository.findAll(pageable);
	}
	
}
