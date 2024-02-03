package life.macchiato.fishsticks.youtube.services;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import life.macchiato.fishsticks.youtube.controllers.requests.SearchRequest;
import life.macchiato.fishsticks.youtube.models.SearchResponse;
import life.macchiato.fishsticks.youtube.models.SearchResult;
import life.macchiato.fishsticks.youtube.repositories.SearchRepository;
import life.macchiato.fishsticks.youtube.utils.GoogleApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class YoutubeService {

    @Autowired
    private SearchRepository searchRepo;

    public SearchResponse recentFromQuery(String query) {
        return null;
    }

    public SearchResponse searchList(SearchRequest searchRequest) throws GeneralSecurityException, IOException {
        YouTube youtubeApi = GoogleApiUtil.getYoutubeService();

        SearchListResponse searchList = youtubeApi.search().list("snippet")
                .setQ(searchRequest.query())
                .setPageToken(searchRequest.pageToken())
                .execute();

        List<SearchResult> results = searchList.getItems().stream()
                .map(SearchResult::from)
                .collect(Collectors.toList());

        return SearchResponse.builder()
                .etag(searchList.getEtag())
                .nextPageToken(searchList.getNextPageToken())
                .previousPageToken(searchList.getPrevPageToken())
                .query(searchRequest.query())
                .results(results)
                .build();

    }

    public void saveResponse(SearchResponse response) {
    }
}
