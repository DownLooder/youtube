package life.macchiato.download.utils;

import life.macchiato.ytdlp.DownloadProgressCallback;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class Progress implements DownloadProgressCallback {

    private final Instant start = Instant.now();

    @Override
    public void onProgressUpdate(float progress, long eta) {
        if (progress == 0) return;
        if (progress == 100)
        {
            log.info("Download complete");
            return;
        }
        if (eta > 30 && progress % 10 == 0) {
            Instant elapsed = Instant.now().minusMillis(start.toEpochMilli());
            log.info("Download progress: {}% elapsed: {}s eta: {}s", progress, elapsed.getEpochSecond(), eta);
        }
    }
}
