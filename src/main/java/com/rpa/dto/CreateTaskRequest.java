package com.rpa.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CreateTaskRequest {
	
	@NotEmpty
	private String name;
	
	private String month;
	
	private String week;
	
	private String day;
	
	private String time;
	
}
