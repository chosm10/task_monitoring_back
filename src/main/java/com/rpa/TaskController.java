package com.rpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rpa.domain.Task;
import com.rpa.domain.TaskSchedule;
import com.rpa.dto.CreateTaskRequest;
import com.rpa.dto.CreateTaskResponse;
import com.rpa.dto.GetAllTasksResponse;
import com.rpa.dto.GetTaskResponse;
import com.rpa.dto.Response;
import com.rpa.dto.UpdateTaskRequest;
import com.rpa.dto.UpdateTaskResponse;
import com.rpa.repository.TaskPageRepository;
import com.rpa.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController {
	private final TaskService taskService;
	private final TaskPageRepository taskPageRepository;
	/*
	 * postman으로 테스트 할때 크롬 cors플러그인 활성화 하고 포스트맨 접속해야 가능
	 * */
	@PostMapping("api/task")
	public Response createTask(@RequestBody @Valid CreateTaskRequest request) {

		Task task = new Task();
		task.setName(request.getName().trim());

		TaskSchedule schedule = TaskSchedule.getSchedule(request.getMonth(), request.getWeek()
				, request.getDay());
		task.setTaskSchedule(schedule);
		
		task.setScheduleTime(request.getTime());

		CreateTaskResponse data = CreateTaskResponse.builder().build();

		Long id = taskService.join(task);			
		data.setId(id);


		return new Response(data);
	}

	@GetMapping("api/tasks")
	public Response getAllTasks() {

		GetAllTasksResponse data = GetAllTasksResponse.builder().build();
		List<Task> tasks = taskService.findAllTasks();
		//			Task도 entity가 아닌 DTO로 맵핑해서 반환
		List<GetTaskResponse> taskResponses = tasks.stream()
				.map(t -> new GetTaskResponse(t.getTaskId(), t.getName(), t.getTaskSchedule(), t.getScheduleTime()))
				.collect(Collectors.toList());
		data.setTasks(taskResponses);


		return new Response(data);
	}

	@PutMapping("api/task/{id}")
	public Response updateTask(@PathVariable("id") Long id,
			@RequestBody @Valid UpdateTaskRequest request) {
		
		Task task = new Task();
		task.setTaskId(id);
		task.setTaskSchedule(TaskSchedule.getSchedule(request.getMonth(), request.getWeek(), request.getDay()));
		task.setScheduleTime(request.getTime());
		Long res_id = taskService.update(task);
		
		UpdateTaskResponse data = UpdateTaskResponse.builder().build();
		data.setId(res_id);
		
		return new Response(data);
	}
	
	@GetMapping("api/task-page")
	public Response getAllTask(final Pageable pageable) {
//		여기 넘어오는 페이지 번호는 원래 번호 -1로 와야함
		Page<Task> page = taskPageRepository.findAll(pageable);
		GetAllTasksResponse data = GetAllTasksResponse.builder().build();
		List<Task> tasks = page.getContent();
		//			Task도 entity가 아닌 DTO로 맵핑해서 반환
		List<GetTaskResponse> taskResponses = tasks.stream()
				.map(t -> new GetTaskResponse(t.getTaskId(), t.getName(), t.getTaskSchedule(), t.getScheduleTime()))
				.collect(Collectors.toList());
		
		data.setTasks(taskResponses);
//		int start = page.getNumber() + 1;
//		int end = (start + BLOCK_NUM - 1 > page.getTotalPages())?page.getTotalPages():start + BLOCK_NUM - 1;
//		data.setStartPage(start);
//		data.setEndPage(end);
		data.setTotalElementNum(page.getTotalElements());
		data.setTotalPage(page.getTotalPages());
		
		return new Response(data);
	}
	
	@GetMapping("api/tasks/{name}")
	public Response getAllTask(@PathVariable("name") String name) {
//		여기 넘어오는 페이지 번호는 원래 번호 -1로 와야함
		List<Task> tasks = taskPageRepository.findByNameContaining(name);
		GetAllTasksResponse data = GetAllTasksResponse.builder().build();
		//			Task도 entity가 아닌 DTO로 맵핑해서 반환
		List<GetTaskResponse> taskResponses = tasks.stream()
				.map(t -> new GetTaskResponse(t.getTaskId(), t.getName(), t.getTaskSchedule(), t.getScheduleTime()))
				.collect(Collectors.toList());
		
		data.setTasks(taskResponses);
		
		return new Response(data);
	}
}
