package com.rpa.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rpa.domain.Task;
import com.rpa.exception.RestException;
import com.rpa.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {
	
	private final TaskRepository taskRepository;
	
//	업무 추가
	@Transactional // 변경 시 필요
	public Long join(Task task) {
		
		validateDuplicateTask(task);
		try {
			taskRepository.save(task);
		} catch (Exception e) {
			throw new RestException(HttpStatus.CONFLICT, "업무 저장 중 이슈가 발생했습니다");
		}
		
		return task.getTaskId();
	}
	
	@Transactional
	public Long update(Task task) {
		
		Task findTask = taskRepository.findOne(task.getTaskId());
		try {
			findTask.setTaskSchedule(task.getTaskSchedule());
			findTask.setScheduleTime(task.getScheduleTime());
		} catch (Exception e) {
			throw new RestException(HttpStatus.CONFLICT, "업무 정보를 갱신하지 못하였습니다.");
		}
		
		return findTask.getTaskId();
	}
	
	public Task findTaskById(Long taskId) {
		
		try {
			return taskRepository.findOne(taskId);
		} catch (Exception e) {
			throw new RestException(HttpStatus.BAD_REQUEST, "업무가 존재하지 않습니다.");
		}
		
	}
	
	//전체 업무 조회
	public List<Task> findAllTasks() {
		
		try {
			return taskRepository.findAll();
		} catch (Exception e) {
			throw new RestException(HttpStatus.CONFLICT, "전체 업무 조회중 이슈가 발생했습니다.");
		}
		
	}
	
	private void validateDuplicateTask(Task task) {
		
		List<Task> findTasks = taskRepository.findByName(task.getName());
		if(!findTasks.isEmpty()) {
			throw new RestException(HttpStatus.BAD_REQUEST, "이미 존재하는 업무 입니다.");
		}
		
	}
}
