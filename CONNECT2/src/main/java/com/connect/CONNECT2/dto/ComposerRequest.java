package com.connect.CONNECT2.dto;

import com.connect.CONNECT2.enums.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComposerRequest {

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(min = 6, max = 245)
    private String password;

    @NotBlank
    @Email
    @Size(max = 150)
    private String emailAddress;

    @NotNull
    private Roles role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    // Optional list of music entries to create with composer
    private List<MusicRequest> music;
}