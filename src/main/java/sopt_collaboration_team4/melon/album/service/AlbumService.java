package sopt_collaboration_team4.melon.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt_collaboration_team4.melon.album.Album;
import sopt_collaboration_team4.melon.album.dto.AlbumResponse;
import sopt_collaboration_team4.melon.album.repository.AlbumRepository;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumResponse getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없엉"));
        return AlbumResponse.from(album);
    }
}
