package life.macchiato.youtube.services;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import life.macchiato.common.requests.SearchRequest;
import life.macchiato.youtube.models.SearchResponse;
import life.macchiato.youtube.models.VideoResult;
import life.macchiato.youtube.repositories.SearchRepository;
import life.macchiato.youtube.utils.GoogleApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class YoutubeService {

    @Autowired
    private SearchRepository searchRepo;

    public SearchResponse recentFromQuery(String query) {
        return null;
    }

    public SearchResponse searchList(SearchRequest request) throws GeneralSecurityException, IOException {
        log.info("new video info request '{}'", request.query());

        YouTube youtubeApi = GoogleApiUtil.getYoutubeService();

        SearchListResponse searchList = youtubeApi.search().list("snippet")
                .setQ(request.query())
                .setPageToken(request.pageToken())
                .execute();

        List<VideoResult> results = new ArrayList<>();

        for (SearchResult result : searchList.getItems())
        {
            switch(result.getId().getKind())
            {
                case "youtube#playlist" -> log.info("playlist {}",result);
                case "youtube#video" -> results.add(from(result));
                default -> log.info("unknown result {}", result);
            }
        }

        return SearchResponse.builder()
                .etag(searchList.getEtag())
                .nextPageToken(searchList.getNextPageToken())
                .previousPageToken(searchList.getPrevPageToken())
                .query(request.query())
                .results(results)
                .build();

    }

    public void saveResponse(SearchResponse response) {
    }

    private VideoResult from(SearchResult result)
    {
        final String videoId = result.getId().getVideoId();
        if (videoId == null) return null;
        return new VideoResult.builder(videoId)
                .etag(result.getEtag())
                .title(result.getSnippet().getTitle())
                .thumbnail(result.getSnippet().getThumbnails().getDefault().getUrl())
                .build();
    }
}
