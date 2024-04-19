package com.communicate_craft.model;

import com.communicate_craft.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Messages")
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "MessageId")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "chat_chat_id", nullable = false)
    @JsonIgnore
    private Chat chat;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonIgnore
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "MessageType")
    private MessageType messageType;

    @Column(name = "SentAt", nullable = false)
    private LocalDateTime sentAt;

    @Size(max = 1024, message = "message is too long")
    @NotEmpty(message = "message can't be empty")
    @Column(name = "Content")
    private String content;

    public Message() {
        sentAt = LocalDateTime.now();
    }

    @JsonProperty
    public String getSender() {
        return user.getUsername();
    }
}
