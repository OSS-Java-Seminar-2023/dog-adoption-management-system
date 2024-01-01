package com.dog.adoption.services.implementations;

import com.dog.adoption.models.Waiting_List;
import com.dog.adoption.repository.WaitingListRepository;
import com.dog.adoption.services.Waiting_ListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class Waiting_ListServiceImpl implements Waiting_ListService {
    private final WaitingListRepository waitingListRepository;

    @Override
    public Waiting_List saveWaitingList(Waiting_List waitingList) {
        return waitingListRepository.save(waitingList);
    }

    public List<Waiting_List> getWaitingListByDogId(UUID dogId) {
        return waitingListRepository.findByDogId(dogId);
    }
}
