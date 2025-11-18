package com.connect.CONNECT2.service;

import com.connect.CONNECT2.dto.ComposerRequest;
import com.connect.CONNECT2.dto.ComposerResponse;
import com.connect.CONNECT2.dto.MusicResponse;
import com.connect.CONNECT2.entity.Composer;
import com.connect.CONNECT2.entity.Music;
import com.connect.CONNECT2.repository.ComposerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComposerService {

    private final ComposerRepository composerRepository;


    public List<ComposerResponse> getAllComposersWithMusic(){
        List<Composer> composers = composerRepository.findAll();

        return composers.stream()
                .map(this::toComposerResponse)
                .collect(Collectors.toList());
    }


    public ComposerResponse toComposerResponse(Composer composer){
        List<MusicResponse> musicResponses = composer.getMusic() == null ? List.of() :
                composer.getMusic().stream()
                        .map(music -> MusicResponse.builder()
                                .id(music.getId())
                                .album(music.getAlbum())
                                .description(music.getDescription())
                                .build())
                        .collect(Collectors.toList());

        return ComposerResponse.builder()
                .id(composer.getId())
                .username(composer.getUsername())
                .emailAddress(composer.getEmailAddress())
                .role(composer.getRole())
                .created(composer.getCreated())
                .music(musicResponses)
                .build();
    }


    @Transactional
    public ComposerResponse createComposerWithMusic(ComposerRequest req) {
        Composer entity = new Composer();
        entity.setUsername(req.getUsername());
        entity.setPassword(req.getPassword());
        entity.setEmailAddress(req.getEmailAddress());
        entity.setRole(req.getRole());
        entity.setCreated(req.getCreated() == null ? LocalDate.now() : req.getCreated());

        if (req.getMusic() != null && !req.getMusic().isEmpty()) {
            List<Music> mus = req.getMusic().stream().map(mr -> {
                Music mm = new Music();
                mm.setAlbum(mr.getAlbum());
                mm.setDescription(mr.getDescription());
                mm.setComposer(entity); // backref
                return mm;
            }).collect(Collectors.toList());
            entity.setMusic(mus);
        }

        Composer saved = composerRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public ComposerResponse updateComposerWithMusic(Long id, ComposerRequest req) {
        Composer existing = composerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Composer not found with id: " + id));

        // update scalar fields
        existing.setUsername(req.getUsername());
        existing.setEmailAddress(req.getEmailAddress());
        existing.setRole(req.getRole());
        existing.setCreated(req.getCreated() == null ? existing.getCreated() : req.getCreated());

        // update password only if provided (non-null & non-blank)
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            existing.setPassword(req.getPassword());
        }

        // Replace music list: orphanRemoval=true on entity will delete removed entries
        existing.getMusic().clear();
        if (req.getMusic() != null && !req.getMusic().isEmpty()) {
            List<Music> mus = req.getMusic().stream().map(mr -> {
                Music mm = new Music();
                mm.setAlbum(mr.getAlbum());
                mm.setDescription(mr.getDescription());
                mm.setComposer(existing);
                return mm;
            }).collect(Collectors.toList());
            existing.getMusic().addAll(mus);
        }

        Composer saved = composerRepository.save(existing);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteComposerById(Long id) {
        Composer existing = composerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Composer not found with id: " + id));
        composerRepository.delete(existing);
    }

    // mapping helper using Lombok @Builder DTOs style
    private ComposerResponse mapToResponse(Composer c) {
        List<MusicResponse> mus = c.getMusic() == null ? List.of() :
                c.getMusic().stream()
                        .map(m -> MusicResponse.builder()
                                .id(m.getId())
                                .album(m.getAlbum())
                                .description(m.getDescription())
                                .build())
                        .collect(Collectors.toList());

        return ComposerResponse.builder()
                .id(c.getId())
                .username(c.getUsername())
                .emailAddress(c.getEmailAddress())
                .role(c.getRole())
                .created(c.getCreated())
                .music(mus)
                .build();
    }
}
