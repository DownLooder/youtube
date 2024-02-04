package life.macchiato.youtube.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString
@Table(name = "VIDEO_RESULT")
public class VideoResult extends SearchListResult {

    private static final String BASE_VIDEO_URL = "https://www.youtube.com/watch?v=";

    private String videoId;
    private String webpageUrl;

    public VideoResult() {
    }

    public VideoResult(builder b) {
        super(b);
        videoId = b.videoId;
        webpageUrl = BASE_VIDEO_URL.concat(videoId);
    }

    public static class builder extends SearchListResult.builder<builder>{

        private final String videoId;

        public builder(String videoId) {
            this.videoId = Objects.requireNonNull(videoId);
        }

        @Override
        public builder self() { return this; }

        @Override
        public VideoResult build() {
            return new VideoResult(this);
        }
    }

}

