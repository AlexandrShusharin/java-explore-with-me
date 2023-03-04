package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.model.UserMapper;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers(List<Long> usersIdList, int from, int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size);
        return userRepository.findAllByIdIn(usersIdList, pageable).stream()
                .map(UserMapper::fromUserToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        return UserMapper.fromUserToUserDto(userRepository.save(UserMapper.fromUserDtoToUser(userDto)));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    private int getPageNumber(int from, int size) {
        return from / size;
    }
}
