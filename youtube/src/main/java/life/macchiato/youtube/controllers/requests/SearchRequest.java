package life.macchiato.youtube.controllers.requests;

import javax.annotation.Nullable;

public record SearchRequest(String query, @Nullable String pageToken) {
}
