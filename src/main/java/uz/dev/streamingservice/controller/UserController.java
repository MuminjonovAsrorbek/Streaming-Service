package uz.dev.streamingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.streamingservice.dto.request.BuySubscriptionDTO;
import uz.dev.streamingservice.dto.response.*;
import uz.dev.streamingservice.service.template.UserService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 20:32
 **/


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long id, @RequestBody BuySubscriptionDTO subscriptionDTO) {

        userService.buySubscription(id, subscriptionDTO);

        return ResponseEntity.ok("Subscribe buy successfully");
    }

    @GetMapping("/{id}/subscription")
    public SubscriptionDTO getUserSubscription(@PathVariable Long id) {

        return userService.getUserSubscription(id);

    }

    @PatchMapping("/{id}/subscription/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {

        userService.cancelSubscription(id);

        return ResponseEntity.ok("Cancel subscription successfully");

    }

    @GetMapping("/{id}/history")
    public List<WatchHistoryDTO> getUserWatchHistory(@PathVariable Long id) {

        return userService.getUserWatchHistory(id);

    }

    @GetMapping("/{id}/recommendations")
    public List<ContentDTO> getRecommendation(@PathVariable Long id) {

        return userService.getRecommendations(id);

    }

    @GetMapping
    public PageableDTO getAllUsers(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page) {

        return userService.getAll(page, 10);

    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {

        return userService.getById(id);

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {

        userService.create(userDTO);

        return ResponseEntity.ok("User created successfully");

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        userService.update(userDTO, id);

        return ResponseEntity.ok("User updated successfully");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.delete(id);

        return ResponseEntity.ok("User deleted successfully");

    }

    @GetMapping("/{id}/continue-watching")
    public List<ContinueWatchingDTO> getUserWatching(@PathVariable Long id) {

        return userService.getUserWatching(id);

    }

}