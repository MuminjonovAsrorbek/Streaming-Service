package uz.dev.streamingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.streamingservice.dto.response.ContentDTO;
import uz.dev.streamingservice.dto.response.PageableDTO;
import uz.dev.streamingservice.dto.request.*;
import uz.dev.streamingservice.service.template.ContentService;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 17:08
 **/


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    @GetMapping
    public PageableDTO getAllContents(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page) {

        return contentService.getAll(page, 10);

    }

    @GetMapping("/filter")
    public PageableDTO getAllContents(ContentFilterDTO contentDTO) {

        return contentService.getAllByFilter(contentDTO);

    }

    @GetMapping("/{id}")
    public ContentDTO getContent(@PathVariable Long id) {

        return contentService.getById(id);

    }

    @PostMapping
    public ResponseEntity<?> createContent(@RequestBody @Valid CreateContentDTO contentDTO) {

        contentService.create(contentDTO);

        return ResponseEntity.ok("Content created successfully");

    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateContent(@RequestBody @Valid CreateContentDTO contentDTO, @PathVariable Long id) {

        contentService.update(contentDTO, id);

        return ResponseEntity.ok("Content updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable Long id) {

        contentService.delete(id);

        return ResponseEntity.ok("Content deleted successfully");
    }

    @PostMapping("/{contentId}/rate")
    public ResponseEntity<?> rateContent(@PathVariable Long contentId, @RequestBody RateContentDTO rateContentDTO) {

        contentService.rateContent(contentId, rateContentDTO);

        return ResponseEntity.ok("Content rated successfully");
    }

    @PostMapping("/{id}/watch")
    public ResponseEntity<?> watchContent(@PathVariable Long id, @RequestBody WatchContentDTO watchContentDTO) {

        contentService.watchContent(id, watchContentDTO);

        return ResponseEntity.ok("Content watched successfully");

    }

    @GetMapping("/{id}/rating")
    public Double getRating(@PathVariable Long id) {

        return contentService.getAvgRating(id);

    }

    @PostMapping("/{id}/actors")
    public ResponseEntity<?> addActors(@RequestBody @Valid CreateActorInContentDTO actorInContentDTO, @PathVariable Long id) {

        contentService.addActors(actorInContentDTO, id);

        return ResponseEntity.ok("Actors added successfully");
    }

    @PostMapping("/{id}/genres")
    public ResponseEntity<?> addGenres(@RequestBody @Valid CreateGenreInContentDTO genreInContentDTO, @PathVariable Long id) {

        contentService.addGenres(genreInContentDTO, id);

        return ResponseEntity.ok("Genres added successfully");

    }
}