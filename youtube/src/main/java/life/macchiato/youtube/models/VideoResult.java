package life.macchiato.youtube.models;

import com.google.api.client.util.DateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
@Getter
@ToString
@Table(name = "VIDEO_RESULT")
public class VideoResult extends SearchListResult {

    private static final String BASE_VIDEO_URL = "https://www.youtube.com/watch?v=";

    private String videoId;
    private String webpageUrl;
    private LocalDateTime publishDate;

    public VideoResult() {
    }

    public VideoResult(builder b) {
        super(b);
        videoId = b.videoId;
        webpageUrl = BASE_VIDEO_URL.concat(videoId);
        publishDate = b.publishDate;
    }

    public static class builder extends SearchListResult.builder<builder>{

        private final String videoId;
        private LocalDateTime publishDate;

        public builder(String videoId) {
            this.videoId = Objects.requireNonNull(videoId);
        }

        public builder publishDate(DateTime publishedAt) {
            Instant instant = Instant.ofEpochMilli(publishedAt.getValue());
            publishDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return this;
        }

        @Override
        public builder self() { return this; }

        @Override
        public VideoResult build() {
            return new VideoResult(this);
        }
    }

}

