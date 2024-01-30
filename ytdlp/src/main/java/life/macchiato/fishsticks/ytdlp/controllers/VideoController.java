package life.macchiato.fishsticks.ytdlp.controllers;

import com.jfposton.ytdlp.YtDlpException;
import life.macchiato.fishsticks.ytdlp.controllers.requests.VideoRequest;
import life.macchiato.fishsticks.ytdlp.models.Video;
import life.macchiato.fishsticks.ytdlp.services.VideoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/ytdlp")
@AllArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() throws YtDlpException {
        return ResponseEntity.status(200).body(videoService.getVersion());
    }

    @PostMapping
    public ResponseEntity<Video> requestVideo(@RequestBody VideoRequest videoRequest) {
        log.info("new video request {}", videoRequest);
        return ResponseEntity.status(201).body(videoService.requestVideo(videoRequest));
    }
}
