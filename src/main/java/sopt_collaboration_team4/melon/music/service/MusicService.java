package sopt_collaboration_team4.melon.music.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sopt_collaboration_team4.melon.music.Music;
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

}
