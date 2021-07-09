package com.rpa.dto;


import com.rpa.domain.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTaskLogResponse {
	
	private String name;
	
	private String bot_ip;
	
	private String run_time;
	
	private String reserved_time;
	
	private TaskStatus status;
	
}
