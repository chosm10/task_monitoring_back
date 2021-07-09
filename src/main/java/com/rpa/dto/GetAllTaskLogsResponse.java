package com.rpa.dto;

import java.util.HashMap;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllTaskLogsResponse {
	
	@Builder.Default
	private HashMap<String, List<GetTaskLogResponse>> taskLogs = new HashMap<>();
}
