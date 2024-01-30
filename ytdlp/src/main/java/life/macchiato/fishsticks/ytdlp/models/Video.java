package life.macchiato.fishsticks.ytdlp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Video {

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
    String videoId;
    String title;
    String thumbnail;
    String webpageUrl;
    Long duration;
    LocalDateTime downloadedAt;

}
