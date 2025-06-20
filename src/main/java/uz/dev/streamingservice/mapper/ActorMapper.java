package uz.dev.streamingservice.mapper;

import lombok.Getter;
import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.ActorDTO;
import uz.dev.streamingservice.entity.Actor;
import uz.dev.streamingservice.mapper.template.BaseMapper;

import java.util.HashSet;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:42
 **/

@Component
@Getter
public class ActorMapper implements BaseMapper<Actor, ActorDTO> {

    @Override
    public Actor toEntity(ActorDTO actorDTO) {

        return new Actor(
                actorDTO.getFullName(),
                new HashSet<>()
        );

    }

    @Override
    public ActorDTO toDTO(Actor actor) {

        return new ActorDTO(
                actor.getId(),
                actor.getFullName()
        );
    }
}
