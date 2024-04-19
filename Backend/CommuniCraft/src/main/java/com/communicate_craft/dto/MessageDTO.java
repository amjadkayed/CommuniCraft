package com.communicate_craft.dto;

import com.communicate_craft.enums.MessageType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    @NotNull
    private Long chatId;

    @NotNull
    private Long senderId;

    @NotNull
    private MessageType messageType;

    @NotEmpty(message = "message can't be empty")
    private String content;
}
