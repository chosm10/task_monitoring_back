package com.rpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class TaskLog {
	
	@EmbeddedId
	@Column(name = "tasklog_id")
	private TaskLogId logId;
		
	// 실행이 시작된 시간
	@UpdateTimestamp
	@Column(name = "run_time")
	private LocalDateTime runTime;
	
	// 실행 예정 시간
	private String scheduleTime;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
	// 해당 업무를 실행한 봇의 식별값
	@Column(name = "bot_id")
	private Long botId; 
	
	private String botIP;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "task_id")
//	private Task task;
	
	@Override
	public String toString() {
		return "TaskLog [logId=" + logId + ", runTime=" + runTime + ", status=" + status + ", botId=" + botId + "]";
	}
}
