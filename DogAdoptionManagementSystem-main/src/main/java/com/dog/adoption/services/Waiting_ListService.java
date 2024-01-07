package com.dog.adoption.services;

import com.dog.adoption.models.Waiting_List;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface Waiting_ListService {
    Waiting_List saveWaitingList(Waiting_List waitingList);

    List<Waiting_List> getWaitingListByDogId(UUID dogId);

}