package com.dog.adoption.services;

import com.dog.adoption.models.WaitingList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WaitingListService {
    WaitingList saveWaitingList(WaitingList waitingList);
    List<WaitingList> getWaitingListByDogId(UUID dogId);

}