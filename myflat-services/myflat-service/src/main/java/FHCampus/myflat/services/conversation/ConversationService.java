package fhcampus.myflat.services.conversation;

import fhcampus.myflat.dtos.Conversation;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation createConversation(List<User> participants) {
        Conversation conversation = new Conversation();
        conversation.setParticipants(participants);
        return conversationRepository.save(conversation);
    }

    public List<Conversation> getConversationsForUser(User user) {
        return conversationRepository.findByParticipantsContaining(user);
    }
}