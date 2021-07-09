package com.rpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Task {
	@Id @GeneratedValue
	@Column(name = "task_id")
	private Long taskId;
	
	private String name;
	
	@UpdateTimestamp
	private LocalDateTime date;
	
	@Embedded
	private TaskSchedule taskSchedule;
	
	private String scheduleTime;
	
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", name=" + name + ", date=" + date + ", taskSchedule=" + taskSchedule + "]";
	}
	
//	@OneToMany(mappedBy = "task")
//	@Column(name = "task_logs")
//	private List<TaskLog> taskLogs;
}
