package sopt_collaboration_team4.melon.album.dto;

import sopt_collaboration_team4.melon.album.Album;
import sopt_collaboration_team4.melon.music.Music;

import java.util.List;

public class AlbumResponse {

    private Long id;
    private String title;
    private String imageUrl;
    private String artistName;
    private String coverImageUrl;
    private List<Music> musicList;

    public static AlbumResponse from(Album album) {
        AlbumResponse res = new AlbumResponse();
        res.id = album.getId();
        res.title = album.getTitle();
        res.imageUrl = album.getImageUrl();
        res.artistName = album.getArtistName();
        res.coverImageUrl = album.getCoverImageUrl();
        return res;
    }
}
