package com.rpa.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetResourceLogResponse {
	
	private String ip;
	
	private String cpu;
	
	private String memory;
	
	private String disk;
	
	private String err;
	
	private LocalDateTime time;
}
