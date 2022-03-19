package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DragDropRetroSprintCommentDto {

    private String dragDropCommentId;
    private String fromGroup;
    private String toGroup;
    private Set<RetroSprintCommentDto> commentsUpdatedPosition;
}
