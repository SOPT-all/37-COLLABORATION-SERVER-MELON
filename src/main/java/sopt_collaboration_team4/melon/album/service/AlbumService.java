package sopt_collaboration_team4.melon.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt_collaboration_team4.melon.album.Album;
import sopt_collaboration_team4.melon.album.dto.AlbumResponse;
import sopt_collaboration_team4.melon.album.repository.AlbumRepository;
import sopt_collaboration_team4.melon.global.exception.ApiException;

import static sopt_collaboration_team4.melon.global.exception.ErrorCode.ALBUM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumResponse getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ApiException(ALBUM_NOT_FOUND));
        return AlbumResponse.from(album);
    }
}
