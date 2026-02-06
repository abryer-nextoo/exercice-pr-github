package fr.nextoo.devfest2024_back.controller;

import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.enumeration.House;
import fr.nextoo.devfest2024_back.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    public UsersController(
            final UserService userService
    ){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserEntity>> getUsers(@RequestParam() int page) {
        return new ResponseEntity<>(userService.getAllWithPage(page), HttpStatus.OK);
    }

    @GetMapping("/score")
    public ResponseEntity<Map<String,Integer>> getAllUsers() {
        return new ResponseEntity<>(userService.getScores(), HttpStatus.OK);
    }

    @GetMapping("/{house}")
    public ResponseEntity<Page<UserEntity>> getUsersWithHouse(@RequestParam() int page, @PathVariable House house) {
        return new ResponseEntity<>(userService.getAllWithHouse(page,house),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserEntity> postUser(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(userService.addUser(userEntity),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUsers() {
        userService.deleteAll();
        return new ResponseEntity<>("Tous les participant sont suprimer", HttpStatus.OK);
    }

    @GetMapping("/winner")
    public ResponseEntity<UserEntity> getWinner() {
        return new ResponseEntity<>(userService.findWinner(),HttpStatus.OK);
    }
}
