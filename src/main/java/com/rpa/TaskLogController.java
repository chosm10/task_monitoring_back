package com.rpa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rpa.domain.TaskLog;
import com.rpa.domain.TaskLogId;
import com.rpa.domain.TaskStatus;
import com.rpa.dto.GetAllTaskLogsResponse;
import com.rpa.dto.GetTaskLogResponse;
import com.rpa.dto.Response;
import com.rpa.dto.UpdateTaskLogRequest;
import com.rpa.dto.UpdateTaskLogResponse;
import com.rpa.exception.RestException;
import com.rpa.service.TaskLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskLogController {
	private final TaskLogService taskLogService;

	/*
	 * postman으로 테스트 할때 크롬 cors플러그인 활성화 하고 포스트맨 접속해야 가능
	 * */
	@PutMapping("api/task-log")
	public Response updateTaskLog(@RequestBody @Valid UpdateTaskLogRequest request) {

		TaskLog log = new TaskLog();
		log.setLogId(new TaskLogId(request.getName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
		log.setBotId(request.getBotId());
		log.setBotIP(request.getBotIp());
		log.setRunTime(LocalDateTime.now());
		TaskStatus s = null;

		switch (request.getStatus().toLowerCase()) {
		case "comp":
			s = TaskStatus.COMP;
			break;
		case "run":
			s = TaskStatus.RUN;
			break;
		case "fail":
			s = TaskStatus.FAIL;
			break;
		default:
			throw new RestException(HttpStatus.BAD_REQUEST, "입력받은 status의 형식이 올바르지 못합니다.");
		}
		log.setStatus(s);

		String name = taskLogService.join(log).getName();
		UpdateTaskLogResponse data = UpdateTaskLogResponse.builder().build();
		data.setName(name);

		return new Response(data);
	}

	@GetMapping("api/task-logs")
	public Response getAllTaskLogs() {

		List<TaskLog> taskLogs = taskLogService.findAllTasksByDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		GetAllTaskLogsResponse data = GetAllTaskLogsResponse.builder().build();
		List<GetTaskLogResponse> taskLog = taskLogs.stream()
				.map(t -> new GetTaskLogResponse(t.getLogId().getName(), t.getBotIP(),
						t.getRunTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")), t.getScheduleTime(), t.getStatus()))
				.collect(Collectors.toList());
		
		HashMap<String, List<GetTaskLogResponse>> map = new HashMap<>();
		map.put("WAIT", new ArrayList<GetTaskLogResponse>());
		// List에서 Bot_ip를 키로 맵을 만들어서 반환
		for(GetTaskLogResponse t: taskLog) {
			if(t.getBot_ip() != null) {
				if(map.containsKey(t.getBot_ip())) {
					map.get(t.getBot_ip()).add(t);
				} else {
					List<GetTaskLogResponse> val = new ArrayList<GetTaskLogResponse>();
					val.add(t);
					map.put(t.getBot_ip(), val);
				}
			} else {
				map.get("WAIT").add(t);
			}
		}
		
		data.setTaskLogs(map);

		return new Response(data);
	}

}
