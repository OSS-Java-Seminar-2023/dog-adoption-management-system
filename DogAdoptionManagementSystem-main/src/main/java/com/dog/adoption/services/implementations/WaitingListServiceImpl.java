package com.dog.adoption.services.implementations;

import com.dog.adoption.models.WaitingList;
import com.dog.adoption.repository.WaitingListRepository;
import com.dog.adoption.services.WaitingListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WaitingListServiceImpl implements WaitingListService {
    private final WaitingListRepository waitingListRepository;

    @Override
    public WaitingList saveWaitingList(WaitingList waitingList) {
        return waitingListRepository.save(waitingList);
    }

    public List<WaitingList> getWaitingListByDogId(UUID dogId) {
        return waitingListRepository.findByDogId(dogId);
    }
}
