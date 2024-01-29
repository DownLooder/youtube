package life.macchiato.fishsticks.youtube.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "resp_id")
    private Long id;
    private String etag;
    private String nextPageToken;
    private String previousPageToken;
    private String query;
    private List<SearchResult> results;
}
