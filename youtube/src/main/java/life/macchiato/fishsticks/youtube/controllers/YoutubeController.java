package life.macchiato.fishsticks.youtube.controllers;

import life.macchiato.fishsticks.youtube.controllers.requests.SearchRequest;
import life.macchiato.fishsticks.youtube.models.SearchResponse;
import life.macchiato.fishsticks.youtube.services.YoutubeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@RestController
@RequestMapping("api/v1/yt")
@AllArgsConstructor
public class YoutubeController {

    @Autowired
    private final YoutubeService youtubeService;

    @GetMapping
    public ResponseEntity<String> hello()
    {
        return ResponseEntity.status(200).body("What's up there?");
    }

    @PostMapping
    public ResponseEntity<SearchResponse> searchList(@RequestBody SearchRequest searchRequest)
    {
//        todo: find "recent" search list
        SearchResponse recent = youtubeService.recentFromQuery(searchRequest.query());
        if (recent == null)
        {
            SearchResponse searchListResponse = null;
            try {
                searchListResponse = youtubeService.searchList(searchRequest);
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }

            youtubeService.saveResponse(searchListResponse);
            return ResponseEntity.status(201).body(searchListResponse);
        }

        return ResponseEntity.status(201).body(recent);
    }

}
