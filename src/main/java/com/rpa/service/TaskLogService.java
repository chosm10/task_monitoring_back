package com.rpa.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rpa.domain.TaskLog;
import com.rpa.domain.TaskLogId;
import com.rpa.exception.RestException;
import com.rpa.repository.TaskLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskLogService {
	
	private final TaskLogRepository taskLogRepository;
//	업무 추가
	@Transactional // 변경 시 필요
	public TaskLogId join(TaskLog taskLog) {
		try {
			TaskLog find = taskLogRepository.findOne(taskLog.getLogId());
			// 이미 존재하면 업데이트 / 아니면 저장
			if(find != null) {
				find.setRunTime(taskLog.getRunTime());
				find.setBotId(taskLog.getBotId());
				find.setBotIP(taskLog.getBotIP());
				find.setStatus(taskLog.getStatus());
				return find.getLogId();
			}
		} catch (Exception e) {
			throw new RestException(HttpStatus.BAD_REQUEST, "TaskLog 조회 중 이슈 발생");
		}
		try {
			taskLogRepository.save(taskLog);
			return taskLog.getLogId();
		} catch (Exception e) {
			throw new RestException(HttpStatus.BAD_REQUEST, "TaskLog 저장 중 이슈 발생: ");
		}
	}
	
	public List<TaskLog> findTaskByName(String name) {
		return taskLogRepository.findByName(name);
	}
	
	//전체 업무 조회
	public List<TaskLog> findAllTasksByDate(String rundate) {
		return taskLogRepository.findAllByDate(rundate);
	}
	
}
