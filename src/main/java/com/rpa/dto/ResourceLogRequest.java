package com.rpa.dto;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ResourceLogRequest {
	
	private String ip;
	
	@NotNull
	private String date;
	
}
