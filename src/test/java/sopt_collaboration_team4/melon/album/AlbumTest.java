package sopt_collaboration_team4.melon.album;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sopt_collaboration_team4.melon.music.Music;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AlbumTest {

    @Test
    @DisplayName("Album.addMusic()으로 양방향 관계 지속되는지")
    void addMusicTest() {
        // given
        Album album = new Album.Builder()
                .title("앨범")
                .artistName("아티스트")
                .imageUrl("url")
                .build();

        Music music = Music.create(
                "노래1", "가수1",
                null, "url", "KR", true, LocalDate.now()
        );

        // when
        album.addMusic(music);

        // then
        assertThat(album.getMusics())
                .hasSize(1)
                .containsExactly(music);

        assertThat(music.getAlbum())
                .isNotNull()
                .isEqualTo(album);
    }
}
