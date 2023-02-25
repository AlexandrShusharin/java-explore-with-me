package ru.practicum.user.service;


import ru.practicum.user.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser(long userId);

    List<UserDto> getUsers(List<Long> usersId, int from, int size);

    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    void deleteUser(long userId);
}
