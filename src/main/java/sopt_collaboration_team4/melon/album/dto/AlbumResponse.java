package sopt_collaboration_team4.melon.album.dto;

import java.util.List;
import lombok.Getter;
import sopt_collaboration_team4.melon.album.Album;
import sopt_collaboration_team4.melon.music.dto.MusicResponse;

@Getter
public class AlbumResponse {

    private Long id;
    private String title;
    private String imageUrl;
    private String artistName;
    private String coverImageUrl;
    private List<MusicResponse> musicList;

    public static AlbumResponse from(Album album) {
        AlbumResponse res = new AlbumResponse();
        res.id = album.getId();
        res.title = album.getTitle();
        res.imageUrl = album.getImageUrl();
        res.artistName = album.getArtistName();
        res.coverImageUrl = album.getCoverImageUrl();
        res.musicList = album.getMusics().stream()
                .map(MusicResponse::from)
                .toList();
        return res;
    }
}
