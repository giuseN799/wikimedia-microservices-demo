package net.javaguides.springboot.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.entities.ModerationRequest;
import java.util.List;


public interface ModerationRequestRepository extends JpaRepository<ModerationRequest, String>{

    List<ModerationRequest> findByStatus(String status, Pageable page);
} 