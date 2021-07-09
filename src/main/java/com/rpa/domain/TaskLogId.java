package com.rpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@Table(name = "task_log_id")
@AllArgsConstructor
public class TaskLogId implements Serializable {

	private String name;
	
	@Column(name = "run_date")
	private String runDate;

	public TaskLogId() {}

	static public TaskLogId getTaskLogId(String name, String runDate) {
		TaskLogId t = new TaskLogId();
		t.setName(name);
		t.setRunDate(runDate);

		return t;
	}

	@Override
	public boolean equals(Object o) {
		if(o.equals(this)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}


}
