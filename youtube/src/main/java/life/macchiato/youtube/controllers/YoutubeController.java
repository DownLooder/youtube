package life.macchiato.youtube.controllers;

import life.macchiato.common.requests.SearchRequest;
import life.macchiato.common.requests.VideoRequest;
import life.macchiato.youtube.models.SearchResponse;
import life.macchiato.youtube.services.YoutubeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;

@Slf4j
@RestController
@RequestMapping("api/v1/yt")
@AllArgsConstructor
public class YoutubeController {

    @Autowired
    private final YoutubeService youtubeService;

    @Autowired
    private final RestTemplate restTemplate;

    private static final String YTDLP_URL = "http://ytdlp/api/v1/ytdlp/";

    @GetMapping("/hello")
    public ResponseEntity<String> hello ()
    {
        return ResponseEntity.status(200).body("What's up there?");
    }

    @PostMapping("/info")
    public ResponseEntity<SearchResponse> searchList(@RequestBody SearchRequest request)
    {
        SearchResponse recent = youtubeService.recentFromQuery(request.query());
        if (recent == null)
        {
            SearchResponse searchListResponse = null;
            try {
                searchListResponse = youtubeService.searchList(request);
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }

            youtubeService.saveResponse(searchListResponse);
            return ResponseEntity.status(201).body(searchListResponse);
        }

        return ResponseEntity.status(201).body(recent);
    }
    @PostMapping("/request-video")
    public ResponseEntity<?> requestVideo(@RequestBody VideoRequest videoRequest)
    {
        log.info("new video request {}", videoRequest);
        HashSet formats = restTemplate.postForObject(
                YTDLP_URL + "request",
                videoRequest,
                HashSet.class);

        log.info("formats: {}", formats);
        return ResponseEntity.ok("Requested " + videoRequest.webpageUrl());
    }

}
