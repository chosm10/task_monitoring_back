package com.rpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rpa.domain.Task;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaskServiceTest {
	
	@Autowired
	TaskService taskService;
	
	@Test
	public void 업무추가() throws Exception {
//		given
		Task task = makeTest();
//		when
		Long joinId = taskService.join(task);
		Task task1 = taskService.findTaskById(joinId);
		System.out.println(task1.toString());
//		then
		assertEquals(task, taskService.findTaskById(joinId));
	}
	
	@Test
	public void 업무중복추가() throws Exception {
//		given
		Task task1 = makeTest();
		Task task2 = makeTest();
//		when
		Long joinId1 = taskService.join(task1);
		try {
			Long joinId2 = taskService.join(task2);			
			assertEquals(joinId1, joinId2);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		then
	}
	
	@Test
	public void 업무찾기() throws Exception {
		Task task1 = makeTest();
		Long joinId = taskService.join(task1);
		Task task2 = taskService.findTaskById(joinId);
		assertEquals(task1, task2);
	}
	
	@Test
	public void 모든업무찾기() throws Exception {
		Task task1 = makeTest();
		Task task2 = new Task();
		task2.setName("아디다스 직매입");
		Long joinId1 = taskService.join(task1);
		Long joinId2 = taskService.join(task2);
		List<Task> tasks = taskService.findAllTasks();
		
		assertEquals(joinId1, tasks.get(0).getTaskId());
		assertEquals(joinId2, tasks.get(1).getTaskId());
	}
	
	public Task makeTest() {
		Task task = new Task();
		task.setName("나이키 신상품출고");
		return task;
	}
}
