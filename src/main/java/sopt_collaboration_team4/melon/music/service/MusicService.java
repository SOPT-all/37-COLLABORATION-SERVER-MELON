package sopt_collaboration_team4.melon.music.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sopt_collaboration_team4.melon.music.Music;
import sopt_collaboration_team4.melon.music.dto.MusicResponse;
import sopt_collaboration_team4.melon.music.repository.MusicRepository;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public Page<Music> getPopularMusics(Pageable pageable) {
        return musicRepository.findAllByOrderByPlayCountDesc(pageable);
    }

    //아직 요청사항에는 없음
    public void increasePlayCount(long id) {
        musicRepository.findById(id).ifPresent(Music::increasePlayCount);
    }

    public List<MusicResponse> getNewestMusics(Pageable pageable, String country) {
        if (country == null || country.isEmpty()) {
            return musicRepository.findAllByOrderByReleaseDateDesc(pageable)
                    .stream()
                    .map(MusicResponse::from)
                    .toList();
        } else {
            return musicRepository.findAllByCountryOrderByReleaseDateDesc(country, pageable)
                    .stream()
                    .map(MusicResponse::from)
                    .toList();
        }
    }

    public List<MusicResponse> getMixUpMusics(Pageable pageable) {
        Page<Music> randomMusics = musicRepository.findRandomMusics(pageable);
        return randomMusics.stream()
                .map(MusicResponse::from)
                .toList();
    }

    public List<MusicResponse> getCustomRecommendation(Pageable pageable) {
        Page<Music> recommendedMusics = musicRepository.findRandomMusics(pageable);
        return recommendedMusics.stream()
                .map(MusicResponse::from)
                .toList();
    }
}
