package ru.practicum.comment.model;

import ru.practicum.user.model.UserMapper;

public final class CommentMapper {
    private CommentMapper() {
    }

    public static CommentDto fromCommentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(UserMapper.fromUserToUserShortDto(comment.getAuthor()))
                .created(comment.getCreated())
                .build();
    }

    public static Comment fromCommentAddDtoToComment(CommentAddDto commentAddDto) {
        return Comment.builder()
                .text(commentAddDto.getText())
                .build();
    }
}
