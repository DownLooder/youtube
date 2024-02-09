package life.macchiato.youtube.repositories;

import life.macchiato.youtube.models.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchRepository extends JpaRepository<SearchResponse, Long> {
    Optional<SearchResponse> findByQuery(String query);
}
