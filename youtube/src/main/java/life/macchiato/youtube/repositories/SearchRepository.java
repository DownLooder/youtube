package life.macchiato.youtube.repositories;

import life.macchiato.youtube.models.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<SearchResponse, Long> {
}
