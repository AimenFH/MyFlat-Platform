package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.Conversation;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.repositories.UserRepository;
import fhcampus.myflat.services.conversation.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Conversation createConversation(@RequestBody List<Long> participantIds) {
        List<User> participants = userRepository.findAllById(participantIds);
        return conversationService.createConversation(participants);
    }

    @GetMapping("/{userId}")
    public List<Conversation> getConversationsForUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return conversationService.getConversationsForUser(user);
    }
}

