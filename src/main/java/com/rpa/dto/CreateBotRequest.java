package com.rpa.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CreateBotRequest {
	
	@NotEmpty
	private String ip;
	
}
