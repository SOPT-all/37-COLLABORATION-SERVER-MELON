package sopt_collaboration_team4.melon.album.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt_collaboration_team4.melon.album.Album;
import sopt_collaboration_team4.melon.album.dto.AlbumResponse;
import sopt_collaboration_team4.melon.album.service.AlbumService;
import sopt_collaboration_team4.melon.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Album")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{albumId}")
    public ApiResponse<Album> getAlbum(Long albumId) {
        AlbumResponse albumById = albumService.getAlbumById(albumId);
        return ApiResponse.onSuccess(HttpStatus.OK, "엘범 데이터 성공적으로 가져옴",albumById);
    }
}
