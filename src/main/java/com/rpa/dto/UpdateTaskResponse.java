package com.rpa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTaskResponse {
	
	@Builder.Default
	private Long id = -1L;
	
}
