package sopt_collaboration_team4.melon.music.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sopt_collaboration_team4.melon.album.Album;
import sopt_collaboration_team4.melon.music.Music;
import sopt_collaboration_team4.melon.music.contorller.MusicController;
import sopt_collaboration_team4.melon.music.service.MusicService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
class MusicControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MusicService musicService;

    @Test
    @DisplayName("인기 차트 조회 => 재생횟수 기준으로 상위8개 반환")
    void getPopularMusic() throws Exception {
        // given
        Album album = new Album.Builder()
                .title("테스트 앨범")
                .artistName("테스트 아티스트")
                .imageUrl("https://example.com/image.jpg")
                .build();

        Music music1 = Music.create(
                "노래1",
                "가수1",
                album,
                "https://example.com/1.jpg",
                "KR",
                true,
                LocalDate.of(2024, 1, 1)
        );
        music1.increasePlayCount();
        music1.increasePlayCount();

        Music music2 = Music.create(
                "노래2", "가수2",
                album, "url2", "KR", false, LocalDate.now()
        );
        music2.increasePlayCount();

        List<Music> musics = List.of(music1, music2);
        Page<Music> page = new PageImpl<>(musics);

        when(musicService.getPopularMusics(any(Pageable.class)))
                .thenReturn(page);

        // when
        var result = mockMvc.perform(
                        get("/api/v1/music/chart")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        // then
        String json = result.getResponse().getContentAsString();

        assertThat(json)
                .contains("노래1")
                .contains("노래2")
                .contains("가수1")
                .contains("가수2");
    }
}
