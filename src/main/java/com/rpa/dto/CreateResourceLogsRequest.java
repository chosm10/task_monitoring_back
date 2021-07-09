package com.rpa.dto;

import java.util.HashMap;

import lombok.Data;

@Data
public class CreateResourceLogsRequest {
	
	private HashMap<String, ResourceLogRequest> map;
	
}
