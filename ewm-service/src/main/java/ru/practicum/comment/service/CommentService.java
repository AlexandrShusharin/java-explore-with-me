package ru.practicum.comment.service;

import ru.practicum.comment.model.CommentAddDto;
import ru.practicum.comment.model.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addComment(long userId, long eventId, CommentAddDto commentAddDto);

    CommentDto getComment(long commentId);

    List<CommentDto> getCommentList(long eventId, int from, int size);

    CommentDto updateComment(long commentId, CommentAddDto commentAddDto);

    void deleteComment(long commentId);
}
