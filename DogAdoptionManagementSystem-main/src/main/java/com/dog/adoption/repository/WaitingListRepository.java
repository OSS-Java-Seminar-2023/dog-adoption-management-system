package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Waiting_List;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface WaitingListRepository extends JpaRepository<Waiting_List, UUID>{
    List<Waiting_List> findByDogId(UUID dogId);
}
