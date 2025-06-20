package uz.dev.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.streamingservice.dto.response.GenreDTO;
import uz.dev.streamingservice.service.template.GenreService;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 12:42
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<?> saveGenre(@RequestBody GenreDTO genreDTO) {

        genreService.create(genreDTO);

        return ResponseEntity.ok("Genre created successfully");

    }

}
