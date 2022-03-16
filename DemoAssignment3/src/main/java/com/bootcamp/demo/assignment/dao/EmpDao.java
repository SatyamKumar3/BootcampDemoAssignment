package com.bootcamp.demo.assignment.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.bootcamp.demo.assignment.entity.Emp;

public interface EmpDao extends CassandraRepository<Emp, Integer> {

}
