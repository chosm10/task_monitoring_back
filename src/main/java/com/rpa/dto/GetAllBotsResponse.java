package com.rpa.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllBotsResponse {
	
	@Builder.Default
	private List<GetBotResponse> bots = new ArrayList<>();
	
	private long totalElementNum;
	
	private int totalPage;
	
}
