package life.macchiato.fishsticks.youtube.services;

import life.macchiato.fishsticks.youtube.controllers.requests.SearchRequest;
import life.macchiato.fishsticks.youtube.models.SearchResponse;
import life.macchiato.fishsticks.youtube.repositories.SearchRepository;
import org.springframework.stereotype.Service;

@Service
public class YoutubeService {

    private SearchRepository searchRepo;

    public SearchResponse recentFromQuery(String query) {
        return null;
    }

    public SearchResponse searchList(SearchRequest searchRequest) {
        return null;
    }

    public void saveResponse(SearchResponse response) {
    }
}
