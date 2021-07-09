package com.rpa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rpa.domain.TaskStatus;
import com.rpa.repository.BotRepository;
import com.rpa.domain.Bot;
import com.rpa.domain.Resource;
import com.rpa.domain.ResourceLog;
import com.rpa.domain.Task;
import com.rpa.domain.TaskLog;
import com.rpa.domain.TaskLogId;
import com.rpa.service.ResourceLogService;
import com.rpa.service.TaskLogService;
import com.rpa.service.TaskService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {
	
	private final TaskService taskService;
	private final TaskLogService taskLogService;
	private final ResourceLogService resourceLogService;
	private final BotRepository botRepository;
	static final int RESC_CHECK_INTERVAL = 1000 * 60 * 10;
	
	@Scheduled(cron = "0 20 00 * * *")
	public void setTaskLog() {
		/*
		 *  당일에 실행되어야할 TASK가 어떤것들인지 등록하기 위한 작업
		 *  TaskService에서 모든 Task 받아와서 실행주기 체크,
		 *  실행 주기에 해당 되면 해당 Task로 상태 WAIT로 해서 TASKLOG 등록
		*/
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<Task> tasks = taskService.findAllTasks();
		for(Task task: tasks) {
			if(task.getTaskSchedule().checkTaskSchedule()) {
				TaskLog log = new TaskLog();
				TaskLogId id =  new TaskLogId();
				id.setName(task.getName());
				id.setRunDate(date);
				
				log.setLogId(id);
				log.setStatus(TaskStatus.WAIT);
				log.setScheduleTime(task.getScheduleTime());
				taskLogService.join(log);
			}
		}
		
	}
	
	@Scheduled(fixedDelay = RESC_CHECK_INTERVAL)
	public void setResourceLog() {
		/*
		 *  자원 현황 체크 주기마다 각 자원에 있는 서버로부터 자원 현황을 받아와서 
		 *  DB에 기록함
		*/
		List<ResourceLog> list = new ArrayList<>();
		List<Bot> bots = botRepository.findAll();
		for(Bot bot: bots) {
			list.add(getResourceLog(bot.getIp()));
		}
		
		resourceLogService.save(list);
	}
	
	private ResourceLog getResourceLog(String ip) {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(3000);
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(100) 
				.setMaxConnPerRoute(5)
				.build(); factory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory); 
		String url = "http://" + ip + ":8081/api/rsc";
		ResourceLog log = new ResourceLog();
		log.setIp(ip);
		try {
			Resource resource = restTemplate.getForObject(url, Resource.class);
			log.setCpu(resource.getCpu());
			log.setMemory(resource.getMemory());
			log.setDisk(resource.getDisk());
		} catch (Exception e) {
			log.setErr("ERROR");
		}
		
		return log;
	}
}
