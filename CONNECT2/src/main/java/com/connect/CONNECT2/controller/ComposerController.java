package com.connect.CONNECT2.controller;

import com.connect.CONNECT2.dto.ComposerRequest;
import com.connect.CONNECT2.dto.ComposerResponse;
import com.connect.CONNECT2.service.ComposerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/composer")
@RequiredArgsConstructor
public class ComposerController {

    private final ComposerService composerService;


    @PostMapping
    public ResponseEntity<ComposerResponse> createComposer(@Valid @RequestBody ComposerRequest request) {
        ComposerResponse created = composerService.createComposerWithMusic(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ComposerResponse> updateComposer(
            @PathVariable("id") Long id,
            @Valid @RequestBody ComposerRequest request) {

        ComposerResponse updated = composerService.updateComposerWithMusic(id, request);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComposer(@PathVariable("id") Long id) {
        composerService.deleteComposerById(id);
    }
}
