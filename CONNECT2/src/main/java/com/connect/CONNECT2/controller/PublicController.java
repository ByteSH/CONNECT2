package com.connect.CONNECT2.controller;

import com.connect.CONNECT2.dto.ComposerResponse;
import com.connect.CONNECT2.service.ComposerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private static final String HEALTH_CHECK = "OK";

    private final ComposerService composerService;

    @GetMapping("/health-check")
    @ResponseStatus(HttpStatus.OK)
    public String getHealthCheck(){
        return HEALTH_CHECK;
    }

    @GetMapping("/composers")
    @ResponseStatus(HttpStatus.OK)
    public List<ComposerResponse> getAll(){
        return composerService.getAllComposersWithMusic();
    }
}
