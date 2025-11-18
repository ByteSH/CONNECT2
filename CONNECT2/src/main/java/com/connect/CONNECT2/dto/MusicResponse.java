package com.connect.CONNECT2.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponse {
    private Long id;
    private String album;
    private String description;
}
