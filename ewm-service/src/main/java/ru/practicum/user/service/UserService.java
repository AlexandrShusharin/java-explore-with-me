package ru.practicum.user.service;

import ru.practicum.user.model.UserDto;

import java.util.List;

public interface UserService {
    void deleteUser(long userId);

    List<UserDto> getUsers(List<Long> usersId, int from, int size);

    UserDto addUser(UserDto userDto);
}
