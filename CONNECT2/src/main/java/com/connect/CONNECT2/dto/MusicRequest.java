package com.connect.CONNECT2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRequest {

    @NotBlank
    @Size(max = 100)
    private String album;

    private String description;
}
