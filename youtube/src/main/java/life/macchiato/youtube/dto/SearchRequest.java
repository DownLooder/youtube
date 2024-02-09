package life.macchiato.youtube.dto;

import javax.annotation.Nullable;

public record SearchRequest(String query, @Nullable String pageToken) {
}
