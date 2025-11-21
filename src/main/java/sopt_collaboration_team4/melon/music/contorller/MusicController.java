package sopt_collaboration_team4.melon.music.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopt_collaboration_team4.melon.global.api.ApiResponse;
import sopt_collaboration_team4.melon.music.dto.MusicResponse;
import sopt_collaboration_team4.melon.music.service.MusicService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/music")
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/chart")
    public List<MusicResponse> getSongsByChart(@PageableDefault(size = 8, sort = "playCount", direction = Sort.Direction.DESC)
                                               Pageable pageable) {
        return musicService.getSongsByChart(pageable)
                .stream()
                .map(MusicResponse::from)
                .toList();
    }

    @GetMapping("/newest")
    public ApiResponse<List<MusicResponse>> getNewestMusic(@PageableDefault(size = 8, sort = "releaseDate", direction = Sort.Direction.DESC)
                                                           Pageable pageable,
                                                           @RequestParam (required = false) String category) {
        return ApiResponse.onSuccess(HttpStatus.OK, "최신 음악 목록을 성공적으로 가져왔습니다.", musicService.getNewestMusics(pageable, category));
    }

    @GetMapping("/popular")
    public ApiResponse<List<MusicResponse>> getPopularMusicForUser(@PageableDefault(size = 9,sort = "releaseDate" , direction = Sort.Direction.DESC)
                                                                   Pageable pageable){
        return ApiResponse.onSuccess(HttpStatus.OK,"사용자에게 인기선곡 데이터들을 성공적으로 반환합니다.",musicService.getPopularMusicForUser(pageable));
    }


    @GetMapping("/mixup")
    public ApiResponse<List<MusicResponse>> getMixUpMusic(@PageableDefault(size = 7, sort = "releaseDate", direction = Sort.Direction.DESC)
                                                          Pageable pageable) {
        return ApiResponse.onSuccess(HttpStatus.OK, "믹스업 음악 목록을 성공적으로 가져왔습니다.", musicService.getMixUpMusics(pageable));
    }

    @GetMapping("/custom-recommendation")
    public ApiResponse<List<MusicResponse>> getCustomRecommendation(@PageableDefault(size = 3, sort = "releaseDate", direction = Sort.Direction.DESC)
                                                                    Pageable pageable) {
        return ApiResponse.onSuccess(HttpStatus.OK, "맞춤 추천 음악 목록을 성공적으로 가져왔습니다.", musicService.getCustomRecommendation(pageable));
    }
}
