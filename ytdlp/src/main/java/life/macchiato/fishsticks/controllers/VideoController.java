package life.macchiato.fishsticks.controllers;

import com.jfposton.ytdlp.YtDlpException;
import life.macchiato.fishsticks.controllers.requests.VideoRequest;
import life.macchiato.fishsticks.models.Video;
import life.macchiato.fishsticks.services.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/ytdlp")
public record VideoController(VideoService videoService) {

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
