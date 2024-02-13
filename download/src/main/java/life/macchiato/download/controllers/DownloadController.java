package life.macchiato.download.controllers;

import life.macchiato.common.requests.DownloadRequest;
import life.macchiato.download.services.DownloadService;
import life.macchiato.ytdlp.YtDlpResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/download")
@AllArgsConstructor
public class DownloadController {

    @Autowired
    private final DownloadService downloadService;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.status(200)
                .body(downloadService.getVersion());
    }

    @PostMapping("/request")
    public ResponseEntity<YtDlpResponse> requestVideo(@RequestBody DownloadRequest downloadRequest) {
        log.info("New video request {}", downloadRequest);
        return ResponseEntity.status(201)
                .body(downloadService.requestVideo(downloadRequest));
    }

    @PostMapping("/formats")
    public ResponseEntity<?> videoFormats(@RequestBody DownloadRequest downloadRequest) {
        log.info("New formats request {}", downloadRequest);
        return ResponseEntity.status(201)
                .body(downloadService.videoFormats(downloadRequest));
    }

    @PostMapping("/test")
    public ResponseEntity<YtDlpResponse> testRequest(@RequestBody DownloadRequest downloadRequest) {
        log.info("New test request {}", downloadRequest);
        return ResponseEntity.status(201)
                .body(downloadService.testRequest(downloadRequest));
    }
}
