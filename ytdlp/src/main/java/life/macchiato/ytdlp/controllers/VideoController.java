package life.macchiato.ytdlp.controllers;

import life.macchiato.common.requests.DownloadRequest;
import life.macchiato.common.responses.LoodResponse;
import life.macchiato.ytdlp.services.VideoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/ytdlp")
@AllArgsConstructor
public class VideoController {

    @Autowired
    private final VideoService videoService;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.status(200)
                .body(videoService.getVersion());
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestVideo(@RequestBody DownloadRequest downloadRequest) {
        log.info("New video request {}", downloadRequest);
        LoodResponse out = videoService.requestVideo(downloadRequest);
        log.info("{}", out);
        return ResponseEntity.status(201).body(out);
    }
    @PostMapping("/formats")
    public ResponseEntity<?> videoFormats(@RequestBody DownloadRequest downloadRequest) {
        log.info("New formats request {}", downloadRequest);
        return ResponseEntity.status(201)
                .body(videoService.videoFormats(downloadRequest));
    }
}
