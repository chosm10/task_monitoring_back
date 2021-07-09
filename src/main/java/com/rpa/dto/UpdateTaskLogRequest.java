package com.rpa.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateTaskLogRequest {
	
	@NotEmpty
	private String name;
	
	@NotNull
	private Long botId;
	
	@NotEmpty
	private String botIp;
	
	@NotEmpty
	private String status;
	
}
