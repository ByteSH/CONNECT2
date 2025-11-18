package com.connect.CONNECT2.dto;


import com.connect.CONNECT2.enums.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComposerResponse {
    private Long id;
    private String username;
    private String emailAddress;
    private Roles role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    private List<MusicResponse> music;
}
