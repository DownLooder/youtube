package life.macchiato.youtube.controllers;

import life.macchiato.common.requests.DownloadRequest;
import life.macchiato.common.responses.LoodResponse;
import life.macchiato.youtube.dto.SearchRequest;
import life.macchiato.youtube.models.SearchResponse;
import life.macchiato.youtube.services.YoutubeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Slf4j
@RestController
@RequestMapping("/api/yt")
@AllArgsConstructor
public class YoutubeController {

    @Autowired
    private final YoutubeService youtubeService;


    @GetMapping("/hello")
    public ResponseEntity<String> hello ()
    {
        return ResponseEntity.status(200).body("What's up there?");
    }

    @PostMapping("/info")
    public ResponseEntity<SearchResponse> searchList(@RequestBody SearchRequest request)
    {
        log.info("New info request {}", request);
        return ResponseEntity.status(201)
                .body(youtubeService.searchList(request));
    }
    @PostMapping("/request")
    public ResponseEntity<?> requestVideo(@RequestBody DownloadRequest downloadRequest)
    {
        log.info("new video request {}", downloadRequest);
        youtubeService.requestVideo(downloadRequest);
        return ResponseEntity.status(201).body("Started download");
    }

}
