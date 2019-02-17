package com.deltabook.repositories;

import com.deltabook.model.Contact;
import com.deltabook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByIsAcceptedAndFriendToId(boolean isAccepted, User friendToId);

    List<Contact> findByIsAcceptedAndFriendFromId(boolean isAccepted, User friendFromId);

    Contact findByFriendFromIdAndFriendToId(User friendFromId, User friendToId);

    Contact findFirstByFriendToIdOrderByCreatedAtDesc(User friendTo);

}
