package edu.fisa.lab.probono.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.fisa.lab.probono.model.entity.ProbonoProject;

@Repository
public interface ProbonoProjectDAO extends JpaRepository<ProbonoProject, Integer>{

}
