package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.CommentAddDto;
import ru.practicum.comment.model.CommentDto;
import ru.practicum.comment.model.CommentMapper;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.InvalidParametersException;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto addComment(long userId, long eventId, CommentAddDto commentAddDto) {
        Comment comment = CommentMapper.fromCommentAddDtoToComment(commentAddDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("User with id=%d was not found", eventId)));
        comment.setAuthor(user);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Event with id=%d was not found", eventId)));
        comment.setEvent(event);
        if (event.getInitiator().getId() == user.getId()) {
            throw new InvalidParametersException(
                    String.format("Comment can`t be published by Event owner."));
        }
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.fromCommentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto getComment(long commentId) {
        return CommentMapper.fromCommentToCommentDto(commentRepository.findById(commentId).orElseThrow(() -> new ObjectNotFoundException(
                String.format("Comment with id=%d was not found", commentId))));
    }

    @Override
    public List<CommentDto> getCommentList(long eventId, int from, int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size, Sort.by("created").descending());
        return commentRepository.findAllByEvent_Id(eventId, pageable).stream()
                .map(CommentMapper::fromCommentToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(long commentId, CommentAddDto commentAddDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ObjectNotFoundException(
                String.format("Comment with id=%d was not found", commentId)));
        if (commentAddDto.getText() != null && !commentAddDto.getText().equals(comment.getText())) {
            comment.setText(commentAddDto.getText());
        }
        return CommentMapper.fromCommentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new ObjectNotFoundException(
                String.format("Comment with id=%d was not found", commentId)));
        commentRepository.deleteById(commentId);
    }

    private int getPageNumber(int from, int size) {
        return from / size;
    }
}
