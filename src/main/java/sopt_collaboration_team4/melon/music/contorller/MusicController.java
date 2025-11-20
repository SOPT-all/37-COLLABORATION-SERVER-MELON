package sopt_collaboration_team4.melon.music.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt_collaboration_team4.melon.music.dto.MusicResponse;
import sopt_collaboration_team4.melon.music.service.MusicService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/music")
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/chart")
    public List<MusicResponse> getPopularMusic(@PageableDefault(size = 8, sort = "playCount", direction = Sort.Direction.DESC)
                                                   Pageable pageable) {
        return musicService.getPopularMusics(pageable)
                .stream()
                .map(MusicResponse::from)
                .toList();
    }
}
