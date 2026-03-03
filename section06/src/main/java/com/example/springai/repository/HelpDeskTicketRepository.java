package com.example.springai.repository;

import com.example.springai.entity.HelpDeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Integer> {
	
	List<HelpDeskTicket> findByUsername(String username);
}
