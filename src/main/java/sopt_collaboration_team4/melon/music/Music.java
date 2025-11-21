package sopt_collaboration_team4.melon.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt_collaboration_team4.melon.album.Album;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String artistName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    private String imageUrl;

    private String country;

    private Boolean isTitle;

    private LocalDate releaseDate;

    private Long playCount = 0L;

    private Music(String title,
                  String artistName,
                  Album album,
                  String imageUrl,
                  String country,
                  Boolean isTitle,
                  LocalDate releaseDate) {
        this.title = title;
        this.artistName = artistName;
        this.album = album;
        this.imageUrl = imageUrl;
        this.country = country;
        this.isTitle = isTitle;
        this.releaseDate = releaseDate;
        this.playCount = 0L;
    }

    public static Music create(String title,
                               String artistName,
                               Album album,
                               String imageUrl,
                               String country,
                               Boolean isTitle,
                               LocalDate releaseDate) {

        return new Music(
                title,
                artistName,
                album,
                imageUrl,
                country,
                isTitle,
                releaseDate
        );
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void increasePlayCount() {
        this.playCount++;
    }

}
