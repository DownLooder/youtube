package life.macchiato.download.repositories;

import life.macchiato.download.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Video, Long>
{
}
