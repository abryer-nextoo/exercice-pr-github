package fr.nextoo.devfest2024_back.controller;

import fr.nextoo.devfest2024_back.dto.CreateUserDTO;
import fr.nextoo.devfest2024_back.dto.UpdateScoreDTO;
import fr.nextoo.devfest2024_back.dto.UserResponseDTO;
import fr.nextoo.devfest2024_back.enumeration.House;
import fr.nextoo.devfest2024_back.service.ScoreService;
import fr.nextoo.devfest2024_back.service.UserService;
import fr.nextoo.devfest2024_back.service.WinnerSelectionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final ScoreService scoreService;
    private final WinnerSelectionService winnerSelectionService;

    public UsersController(
            final UserService userService,
            final ScoreService scoreService,
            WinnerSelectionService winnerSelectionService
    ){
        this.userService = userService;
        this.scoreService = scoreService;
        this.winnerSelectionService = winnerSelectionService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<UserResponseDTO> users = userService.getUsers(page);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/score")
    public ResponseEntity<Map<House, Integer>> getScoresByHouse() {
        Map<House, Integer> scores = scoreService.getScoresByHouse();
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/{house}")
    public ResponseEntity<Page<UserResponseDTO>> getUsersByHouse(
            @RequestParam(defaultValue = "0") int page,
            @PathVariable House house) {
        Page<UserResponseDTO> users = userService.getUsersByHouse(page, house);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(
            @Valid @RequestBody CreateUserDTO createUserDTO
    ) {
        UserResponseDTO user = userService.addUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PatchMapping("/{userId}/score")
    public ResponseEntity<UserResponseDTO> updateScore(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateScoreDTO updateScoreDto) {
        UserResponseDTO user = userService.updateUserScore(userId, updateScoreDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok(Map.of(
                "message", "Tous les participants ont été supprimés"
        ));
    }

    @GetMapping("/winner")
    public ResponseEntity<UserResponseDTO> getWinner() {
        UserResponseDTO winner = winnerSelectionService.selectRandomWinner();
        return ResponseEntity.ok(winner);
    }
}
