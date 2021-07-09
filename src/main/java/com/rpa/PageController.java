package com.rpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpa.domain.Task;
import com.rpa.repository.TaskPageRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PageController {
	private final TaskPageRepository taskPageRepository;
	
	@GetMapping("api/page-size")
	public int getPageSize(final Pageable pageable) {
		return taskPageRepository.findAll(pageable).getContent().size();
	}
	
	@GetMapping("api/page")
	public Page<Task> getPage(final Pageable pageable) {
		return taskPageRepository.findAll(pageable);
	}
}
