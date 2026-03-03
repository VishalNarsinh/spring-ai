package com.example.springai.tools;

import com.example.springai.dto.TicketRequest;
import com.example.springai.entity.HelpDeskTicket;
import com.example.springai.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelpDeskTicketTool {

	private final HelpDeskTicketService helpDeskTicketService;

	@Tool(name = "createTicket", description = "Create the Support Ticket", returnDirect = true)
	String createTicket(@ToolParam(description = "Details to     create a Support ticket") TicketRequest ticketRequest, ToolContext toolContext) {
		String username = (String) toolContext.getContext().get("username");
		log.info("{}", ticketRequest.issue());
		HelpDeskTicket persistedTicket = helpDeskTicketService.createTicket(ticketRequest, username);
		return "Ticket #" + persistedTicket.getId() + " created successfully for user " + persistedTicket.getUsername();
	}

	@Tool(description = "fetch the status of the tickets based on a given username")
	List<HelpDeskTicket> getTicketStatus(ToolContext toolContext) {
		String username = (String) toolContext.getContext().get("username");
//		throw new RuntimeException("Unable to fetch the ticket status");
		return helpDeskTicketService.getTicketsByUsername(username);
	}
}
