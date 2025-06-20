package uz.dev.streamingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.streamingservice.dto.response.ActorDTO;
import uz.dev.streamingservice.service.template.ActorService;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 12:36
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actor")
public class ActorController {

    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<?> createActor(@RequestBody @Valid ActorDTO actorDTO) {

        actorService.create(actorDTO);

        return ResponseEntity.ok("Actor created successfully");

    }

}
