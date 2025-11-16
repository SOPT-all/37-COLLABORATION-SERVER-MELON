package sopt_collaboration_team4.melon.album;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt_collaboration_team4.melon.music.Music;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String imageUrl;

    private String artistName;

    @OneToMany(mappedBy = "album",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Music> musics = new ArrayList<>();

    private Album(Builder builder) {
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
        this.artistName = builder.artistName;
        this.musics = builder.musics;
    }

    public static class Builder {

        private String title;
        private String imageUrl;
        private String artistName;
        private List<Music> musics = new ArrayList<>();

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder artistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder musics(List<Music> musics) {
            this.musics = musics;
            return this;
        }

        public Album build() {
            return new Album(this);
        }
    }

    public void addMusic(Music music) {
        this.musics.add(music);
        music.setAlbum(this);
    }
}
