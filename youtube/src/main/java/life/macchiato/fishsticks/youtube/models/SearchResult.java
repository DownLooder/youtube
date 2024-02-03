package life.macchiato.fishsticks.youtube.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "SEARCH_RESULT")
public class SearchResult {

    private static final String BASE_VIDEO_URL = "https://www.youtube.com/watch?v=";

    @Id
    @SequenceGenerator(
            name = "video_id_sequence",
            sequenceName = "video_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "video_id_sequence"
    )
    Long id;
    String etag;
    String videoId;
    String title;
    String thumbnail;
    String webpageUrl;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    public static SearchResult from(com.google.api.services.youtube.model.SearchResult result) {
        final String videoId = result.getId().getVideoId();
        if (videoId == null) return null;
        return SearchResult.builder()
                .title(result.getSnippet().getTitle())
                .etag(result.getEtag())
                .videoId(videoId)
                .thumbnail(result.getSnippet().getThumbnails().getDefault().getUrl())
                .webpageUrl(BASE_VIDEO_URL.concat(videoId))
                .build();
    }
}
