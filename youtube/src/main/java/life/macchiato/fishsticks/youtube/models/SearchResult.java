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

//    public static SearchResult
}
