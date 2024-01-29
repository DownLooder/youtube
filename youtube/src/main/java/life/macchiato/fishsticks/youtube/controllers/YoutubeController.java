package life.macchiato.fishsticks.youtube.controllers;

import life.macchiato.fishsticks.youtube.controllers.requests.SearchRequest;
import life.macchiato.fishsticks.youtube.models.SearchResponse;
import life.macchiato.fishsticks.youtube.services.YoutubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/yt")
@CrossOrigin
@Slf4j
public class YoutubeController {

    private YoutubeService youtubeService;

    @PostMapping
    public void searchList(@RequestBody SearchRequest searchRequest)
    {
//        todo: find "recent" search list
        SearchResponse recent = youtubeService.recentFromQuery(searchRequest.getQuery());

//        todo: create new search list
        SearchResponse searchListResponse = youtubeService.searchList(searchRequest);

//        todo: use etag result if found in db
//        todo: create response otherwise
        youtubeService.saveResponse(searchListResponse);

    }

}
