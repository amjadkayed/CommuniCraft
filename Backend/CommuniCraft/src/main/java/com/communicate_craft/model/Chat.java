package com.communicate_craft.model;

import com.communicate_craft.enums.ChatType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Chats")
@NoArgsConstructor
@AllArgsConstructor
public class Chat implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ChatId")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ChatType")
    private ChatType chatType;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "Chats_chatParticipants",
            joinColumns = @JoinColumn(name = "chat_ChatId"),
            inverseJoinColumns = @JoinColumn(name = "chatParticipants_UserID"))
    @JsonIgnore
    private Set<User> chatParticipants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages = new LinkedHashSet<>();

    public Chat(ChatType chatType) {
        createdAt = LocalDateTime.now();
        setChatType(chatType);
    }

    @JsonProperty
    List<String> getParticipants() {
        return chatParticipants.stream()
                .map(User::getUsername)
                .toList();
    }
}
