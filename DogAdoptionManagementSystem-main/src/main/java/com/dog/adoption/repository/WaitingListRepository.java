package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.WaitingList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface WaitingListRepository extends JpaRepository<WaitingList, UUID>{
    List<WaitingList> findByDogId(UUID dogId);
}
