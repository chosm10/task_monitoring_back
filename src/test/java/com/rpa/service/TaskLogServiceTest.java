package com.rpa.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rpa.domain.Task;
import com.rpa.domain.TaskLog;
import com.rpa.domain.TaskLogId;
import com.rpa.domain.TaskStatus;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)

public class TaskLogServiceTest {
	
	@Autowired TaskService taskService;
	@Autowired TaskLogService taskLogService;
	@Autowired EntityManager em;
	
	@Test
	public void 당일업무확인() {
//		given
//		업무 생성
		Task task1 = makeTest("나이키 신상품출고");
		Task task2 = makeTest("아디다스 신상품출고");
		Task task3 = makeTest("네이버 정산 김포점");
		Task task4 = makeTest("네이버 이월매출금 동대문점");
		taskService.join(task1);
		taskService.join(task2);
		taskService.join(task3);
		taskService.join(task4);
//		업무 기록 생성
		String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		TaskLog t1 = makeLog(TaskLogId.getTaskLogId("네이버 정산 김포점", today), TaskStatus.COMP, (long)3);
		TaskLog t2 = makeLog(TaskLogId.getTaskLogId("나이키 신상품출고", today), TaskStatus.FAIL, (long)2);
		TaskLog t3 = makeLog(TaskLogId.getTaskLogId("아디다스 신상품출고", today), TaskStatus.RUN, (long)7);
		taskLogService.join(t1);
		taskLogService.join(t2);
		taskLogService.join(t3);
//		when
//		두개 조인한거 받기
//		List<TaskLog> list = 
		List<TaskLog> taskLogs =  taskLogService.findAllTasksByDate(today);
		for(TaskLog t: taskLogs) {
			System.out.println(t.toString());
		}
//		then
//		확인
//		System.out.println(list);
	}
	
	private Task makeTest(String name) {
		Task task = new Task();
		task.setDate(LocalDateTime.now());
		task.setName(name);
		
		return task;
	}
	
	private TaskLog makeLog(TaskLogId id, TaskStatus status, Long botId) {
		TaskLog taskLog = new TaskLog();
		taskLog.setLogId(id);
		taskLog.setRunTime(LocalDateTime.now());
//		taskLog.setTask(task);
		taskLog.setStatus(status);
		taskLog.setBotId(botId);
		return taskLog;
	}
}
