package life.macchiato.common.requests;

import jakarta.annotation.Nullable;

public record SearchRequest(String query, @Nullable String pageToken) {
}
