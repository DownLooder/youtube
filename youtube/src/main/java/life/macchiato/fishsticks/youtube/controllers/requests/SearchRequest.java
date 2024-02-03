package life.macchiato.fishsticks.youtube.controllers.requests;

import javax.annotation.Nullable;

public record SearchRequest(String query, @Nullable String pageToken) {
}
