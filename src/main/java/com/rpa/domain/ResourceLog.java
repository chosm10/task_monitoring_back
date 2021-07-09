package com.rpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ResourceLog {
	
	@Id @GeneratedValue
	private Long id;
	
	private String cpu;
	
	private String memory;
	
	private String disk;
	
	private String err;
	
	@NotEmpty
	private String ip;
	
	@CreationTimestamp
	private LocalDateTime time;
}
