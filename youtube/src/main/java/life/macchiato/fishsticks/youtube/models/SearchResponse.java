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
    private String query;
    private String nextPageToken;
    private String previousPageToken;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_resp_id", referencedColumnName = "resp_id")
    private List<VideoResult> results;
}
