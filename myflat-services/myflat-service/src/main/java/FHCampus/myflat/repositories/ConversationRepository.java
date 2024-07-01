package fhcampus.myflat.repositories;


import fhcampus.myflat.dtos.Conversation;
import fhcampus.myflat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByParticipantsContaining(User user);
}