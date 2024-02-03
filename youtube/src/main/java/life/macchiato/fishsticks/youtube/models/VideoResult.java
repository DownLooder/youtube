package life.macchiato.fishsticks.youtube.models;

import com.google.api.services.youtube.model.SearchResult;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@Entity
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

    public static VideoResult from(SearchResult result) {

        final String videoId = result.getId().getVideoId();
        if (videoId == null) return null;
        return new VideoResult.builder(videoId)
                .etag(result.getEtag())
                .title(result.getSnippet().getTitle())
                .thumbnail(result.getSnippet().getThumbnails().getDefault().getUrl())
                .build();
    }
}
