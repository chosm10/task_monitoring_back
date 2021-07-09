package com.rpa.dto;

import com.rpa.domain.TaskStatus;

import lombok.Data;

@Data
public class TaskJoinDto {
//	private TaskLogId logId;
		
	private TaskStatus status;
	
//	private Long botId; 
	
	private String name;
	
}
