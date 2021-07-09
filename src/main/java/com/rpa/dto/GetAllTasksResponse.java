package com.rpa.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllTasksResponse {
	
	@Builder.Default
	private List<GetTaskResponse> tasks = new ArrayList<>();
	
	private long totalElementNum;
	
	private int totalPage;
	
//	private int startPage;
	
//	private int endPage;
}
