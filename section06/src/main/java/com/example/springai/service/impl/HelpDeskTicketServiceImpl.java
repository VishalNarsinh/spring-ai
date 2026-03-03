package com.example.springai.service.impl;

import com.example.springai.dto.TicketRequest;
import com.example.springai.entity.HelpDeskTicket;
import com.example.springai.repository.HelpDeskTicketRepository;
import com.example.springai.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketServiceImpl implements HelpDeskTicketService {

	private final HelpDeskTicketRepository helpDeskTicketRepository;

	@Override
	public HelpDeskTicket createTicket(TicketRequest tickerInput, String username) {
		 HelpDeskTicket ticket = HelpDeskTicket.builder()
				.issue(tickerInput.issue())
				.username(username)
				.status("OPEN")
				.createdAt(java.time.LocalDateTime.now())
				.eta(java.time.LocalDateTime.now().plusDays(7))
				.build();
		 return helpDeskTicketRepository.save(ticket);
	}

	@Override
	public List<HelpDeskTicket> getTicketsByUsername(String username) {
		return helpDeskTicketRepository.findByUsername(username);
	}
}
