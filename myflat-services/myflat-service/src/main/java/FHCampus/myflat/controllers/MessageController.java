package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.MessageDto;
import fhcampus.myflat.entities.Message;
import fhcampus.myflat.services.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public Message sendMessage(@RequestParam("conversationId") Long conversationId, @RequestBody MessageDto messageDto) {
        System.out.println("Received conversationId: " + conversationId);  // Add this line for debugging
        Long senderId = messageDto.getSenderId();
        String content = messageDto.getContent();
        return messageService.sendMessage(conversationId, senderId, content);
    }

    @GetMapping("/{conversationId}")
    public List<Message> getMessages(@PathVariable Long conversationId) {
        return messageService.getMessages(conversationId);
    }
}
