package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/users")
public class UserAdminController {
    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsersList(@RequestParam(name = "ids", defaultValue = "[]") List<Long> usersIdList,
                                      @RequestParam(name = "from", defaultValue = "0") int from,
                                      @RequestParam(name = "size", defaultValue = "1000000") int size) {
        return userService.getUsers(usersIdList, from, size);
    }
}
