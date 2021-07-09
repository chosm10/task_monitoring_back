package com.rpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.rpa.domain.TaskLog;
import com.rpa.domain.TaskLogId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TaskLogRepository {
	
	private final EntityManager em;
	
	public void save(TaskLog taskLog) {
		em.persist(taskLog);
		em.flush();
	}
	
	public TaskLog findOne(TaskLogId taskLogId) {
		return em.find(TaskLog.class, taskLogId);
	}
	
	public List<TaskLog> findAll() {
		return em.createQuery("select t from TaskLog t", TaskLog.class)
				.getResultList();
	}
	
	public List<TaskLog> findByName(String name) {
		return em.createQuery("select t from TaskLog t where t.id.name = :name",
				TaskLog.class)
				.setParameter("name", name)
				.getResultList();
	}
//	
	public List<TaskLog> findAllByDate(String rundate) {
//		List<Object> arr = em.createQuery("select t2.name,t1.logId.name,t1.botId,t1.status "
//				+ "from TaskLog t1 left outer join t1.task t2 "
//				+ "where t1.logId.runDate = :rundate"
//				)
//				.setParameter("rundate", rundate)
//				.getResultList();
		
		 return em.createQuery("select t from TaskLog t where t.logId.runDate = :rundate"
				, TaskLog.class)
				.setParameter("rundate", rundate)
				.getResultList();
	}
}
