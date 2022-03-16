package com.bootcamp.demo.assignment.dao;

import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.bootcamp.demo.assignment.entity.EmpSkill;;

public interface EmpSkillDao extends CassandraRepository<EmpSkill, EmpSkillId> {
	
	@AllowFiltering
	List<EmpSkill> findByjavaExperienceGreaterThanEqual(Double limit);

}
