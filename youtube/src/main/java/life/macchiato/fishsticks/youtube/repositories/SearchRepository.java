package life.macchiato.fishsticks.youtube.repositories;

import life.macchiato.fishsticks.youtube.models.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<SearchResponse, Long> {
}
