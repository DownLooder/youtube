package life.macchiato.fishsticks.ytdlp.repositories;

import life.macchiato.fishsticks.ytdlp.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long>
{
}
