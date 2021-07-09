package com.rpa.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBotResponse {
	
	private Long Id;
	
	private String ip;
	
	private LocalDateTime time;
	
}
