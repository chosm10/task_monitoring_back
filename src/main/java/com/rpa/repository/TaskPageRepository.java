package com.rpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpa.domain.Task;

@Repository
public interface TaskPageRepository extends JpaRepository<Task, Long>{
	List<Task> findByNameContaining(String name);
}
