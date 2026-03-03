package com.example.springai.service;

import com.example.springai.dto.TicketRequest;
import com.example.springai.entity.HelpDeskTicket;

import java.util.List;

public interface HelpDeskTicketService {
	HelpDeskTicket createTicket(TicketRequest tickerInput, String username);

	List<HelpDeskTicket> getTicketsByUsername(String username);
}
