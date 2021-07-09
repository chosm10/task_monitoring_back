package com.rpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.rpa.domain.Task;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
	
	private final EntityManager em;
	
	public void save(Task task) {
		em.persist(task);
	}
	
	public Task findOne(Long id) {
		return em.find(Task.class, id);
	}
	
	public List<Task> findAll() {
		return em.createQuery("select t from Task t", Task.class)
				.getResultList();
	}
	
	public List<Task> findByName(String name) {
		return em.createQuery("select t from Task t where t.name = :name",
				Task.class)
				.setParameter("name", name)
				.getResultList();
	}
}
