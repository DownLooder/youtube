package life.macchiato.fishsticks.repositories;

import life.macchiato.fishsticks.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long>
{
}
