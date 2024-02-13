package life.macchiato.youtube.services;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import life.macchiato.common.requests.DownloadRequest;
import life.macchiato.common.responses.LoodResponse;
import life.macchiato.youtube.dto.SearchRequest;
import life.macchiato.youtube.models.SearchResponse;
import life.macchiato.youtube.models.VideoResult;
import life.macchiato.youtube.repositories.SearchRepository;
import life.macchiato.youtube.utils.GoogleApiUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class YoutubeService {

    private static final String YTDLP_URL = "http://download/api/download/";

    @Autowired
    private SearchRepository searchRepo;

    private final RestTemplate restTemplate;


    public SearchResponse searchList(SearchRequest request) {

        Optional<SearchResponse> byQuery = searchRepo.findByQuery(request.query());

        if (byQuery.isPresent())
        {
            log.info("Found recent query");
            return byQuery.get();
        }

        SearchListResponse searchList = null;
        try {
            YouTube youtubeApi = GoogleApiUtil.getYoutubeService();
            searchList = youtubeApi.search().list("snippet")
                    .setPageToken(request.pageToken())
                    .setQ(request.query())
                    .execute();
        } catch (GeneralSecurityException | IOException e) {
            log.info("There was an issue: {}", e.getLocalizedMessage());
            return null;
        }

        if (searchList == null) return null;

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

        SearchResponse response = SearchResponse.builder()
                .etag(searchList.getEtag())
                .nextPageToken(searchList.getNextPageToken())
                .previousPageToken(searchList.getPrevPageToken())
                .query(request.query())
                .results(results)
                .build();

        log.info("final response {}", response);

        searchRepo.save(response);
        return response;

    }
    private VideoResult from(SearchResult result)
    {
        final String videoId = result.getId().getVideoId();
        if (videoId == null) return null;
        return new VideoResult.builder(videoId)
                .etag(result.getEtag())
                .title(result.getSnippet().getTitle())
                .publishDate(result.getSnippet().getPublishedAt())
                .thumbnail(result.getSnippet().getThumbnails().getDefault().getUrl())
                .build();
    }

    public void requestVideo(DownloadRequest downloadRequest) {

        LoodResponse lood = restTemplate.postForObject(
                YTDLP_URL + "request",
                downloadRequest,
                LoodResponse.class);
    }
}
