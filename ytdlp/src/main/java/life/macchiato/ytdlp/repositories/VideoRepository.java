package life.macchiato.ytdlp.repositories;

import life.macchiato.ytdlp.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long>
{
}
