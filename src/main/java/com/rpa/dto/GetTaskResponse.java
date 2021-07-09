package com.rpa.dto;

import javax.persistence.Embedded;


import com.rpa.domain.TaskSchedule;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTaskResponse {
	
	private Long Id;
	
	private String name;
	
	@Embedded
	private TaskSchedule schedule;
	
	private String scheduleTime;
}
