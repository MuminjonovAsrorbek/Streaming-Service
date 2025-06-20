package uz.dev.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.streamingservice.dto.response.ActorDTO;
import uz.dev.streamingservice.entity.Actor;
import uz.dev.streamingservice.repository.ActorRepository;
import uz.dev.streamingservice.service.template.ActorService;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 12:39
 **/

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Override
    @Transactional
    public void create(ActorDTO actorDTO) {

        Actor actor = new Actor();

        actor.setFullName(actorDTO.getFullName());

        actorRepository.save(actor);

    }
}
