package com.bootcamp.demo.assignment.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.bootcamp.demo.assignment.entity.Job;


public interface JobDao extends CassandraRepository<Job, String> {

}
