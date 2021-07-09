package com.rpa.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllResourceLogsResponse {
	
	@Builder.Default
	private List<GetResourceLogResponse> history = new ArrayList<>();
	
	private long totalElementNum;
	
	private int totalPage;
	
}
