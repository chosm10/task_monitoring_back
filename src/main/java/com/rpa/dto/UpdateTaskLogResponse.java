package com.rpa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTaskLogResponse {
	
	@Builder.Default
	private String name = "";
	
}
