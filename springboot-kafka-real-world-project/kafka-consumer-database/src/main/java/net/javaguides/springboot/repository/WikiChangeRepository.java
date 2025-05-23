package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.entities.WikiChange;


@Repository
public interface WikiChangeRepository extends JpaRepository<WikiChange, String>{
  
}