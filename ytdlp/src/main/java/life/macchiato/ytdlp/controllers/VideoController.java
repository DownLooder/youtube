package life.macchiato.ytdlp.controllers;

import com.jfposton.ytdlp.YtDlpException;
import life.macchiato.common.requests.VideoRequest;
import life.macchiato.ytdlp.models.Video;
import life.macchiato.ytdlp.services.VideoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/ytdlp")
@AllArgsConstructor
public class VideoController {

    @Autowired
    private final VideoService videoService;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() throws YtDlpException {
        return ResponseEntity.status(200)
                .body(videoService.getVersion());
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestVideo(@RequestBody VideoRequest videoRequest) {
        log.info("New video request {}", videoRequest);
        videoService.requestVideo(videoRequest);
        return ResponseEntity.status(201).build();
    }
    @PostMapping("/formats")
    public ResponseEntity<?> videoFormats(@RequestBody VideoRequest videoRequest) {
        log.info("New formats request {}", videoRequest);
        return ResponseEntity.status(201)
                .body(videoService.videoFormats(videoRequest));
    }
}
