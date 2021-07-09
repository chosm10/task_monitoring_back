package com.rpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@SequenceGenerator(
		name = "BOT_SEQ_GENERATOR",
		sequenceName = "BOT_SEQ",
		initialValue = 1,
		allocationSize = 50)
public class Bot {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOT_SEQ_GENERATOR")
	private Long id;
	
	@NotEmpty
	private String ip;
	
	@CreationTimestamp
	private LocalDateTime time;
}
