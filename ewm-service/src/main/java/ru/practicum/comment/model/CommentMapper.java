package ru.practicum.comment.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.user.model.UserMapper;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class CommentMapper {
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
