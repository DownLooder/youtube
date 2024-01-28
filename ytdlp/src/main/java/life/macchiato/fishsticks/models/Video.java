package life.macchiato.fishsticks.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Video {

    Long id;
    String videoId;
    String title;
    String thumbnail;
    String webpageUrl;
    Long duration;
    LocalDateTime downloadedAt;

}
