package com.rpa.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateTaskRequest {
	
	@NotNull
	private String month;
	
	@NotNull
	private String week;
	
	@NotNull
	private String day;
	
	private String time;
	
}
