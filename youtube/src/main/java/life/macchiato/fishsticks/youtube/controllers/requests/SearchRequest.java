package life.macchiato.fishsticks.youtube.controllers.requests;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public record SearchRequest(String query, String pageToken) {
}