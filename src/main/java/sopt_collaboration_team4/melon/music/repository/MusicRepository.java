package sopt_collaboration_team4.melon.music.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sopt_collaboration_team4.melon.music.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {

    Page<Music> findAllByOrderByPlayCountDesc(Pageable pageable);

    Page<Music> findAllByOrderByReleaseDateDesc(Pageable pageable);

    Page<Music> findAllByCountryOrderByReleaseDateDesc(String country, Pageable pageable);

    @Query("SELECT m FROM Music m ORDER BY function('RAND') ")
    Page<Music> findRandomMusics(Pageable pageable);
}

