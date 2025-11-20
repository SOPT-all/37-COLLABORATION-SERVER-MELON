package sopt_collaboration_team4.melon.music.dto;

import lombok.Getter;
import sopt_collaboration_team4.melon.music.Music;

@Getter
public class MusicResponse {

    private Long id;
    private String title;
    private String artistName;
    private Long playCount;

    public static MusicResponse from(Music music) {
        MusicResponse res = new MusicResponse();
        res.id = music.getId();
        res.title = music.getTitle();
        res.artistName = music.getArtistName();
        res.playCount = music.getPlayCount();
        return res;
    }
}
