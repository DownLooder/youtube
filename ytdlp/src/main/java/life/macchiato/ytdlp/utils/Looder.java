package life.macchiato.ytdlp.utils;

import com.jfposton.ytdlp.*;
import jakarta.annotation.Nullable;
import life.macchiato.common.requests.VideoRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.OutputStream;
import java.time.Instant;

@Slf4j
public class Looder {

    private final YtDlpRequest request;

    public Looder(builder builder) {
        request = builder.request;
    }
    public static class builder {

        private final YtDlpRequest request;

        public builder(VideoRequest videoRequest) {
            request = new YtDlpRequest(videoRequest.webpageUrl());
            String outDir = outDir(videoRequest.format());
            log.info("out dir {}", outDir);
            request.setDirectory(outDir);
            format(videoRequest.format());
        }

        public builder stream() {
            request.setOption("output", "-");
            return this;
        }
        public builder format(String s) {
            request.setOption("format", s);
            return this;
        }

        public Looder build() {
            return new Looder(this);
        }

        public String outDir(String directory) {
            String homeDir = System.getProperty("user.dir");
            String path = "downloads";
            String dir = String.format("%s/%s/%s", homeDir, path, directory);
            File outDir = new File(dir);
            if (!outDir.exists())
            {
                if (outDir.mkdirs()){
                    log.info("created directory {}", dir);
                }
                else
                {
                    log.info("error creating directory {}", dir);
                    return "";
                }
            }
            return dir;
        }
    }

    public YtDlpResponse execute(@Nullable OutputStream outputStream)
    {
        try
        {
            YtDlpResponse response = YtDlp.execute(
                    request,
                    outputStream,
                    new Progress());
            return response;
        }
        catch (YtDlpException e)
        {
            throw new RuntimeException(e);
        }

    }

    public YtDlpResponse execute() {
        return execute(null);
    }

    public static class Progress implements DownloadProgressCallback {

        private final Instant start = Instant.now();

        @Override
        public void onProgressUpdate(float progress, long eta) {
            if (progress == 100 || (eta > 30 && progress % 10 == 0)) {
                Instant elapsed = Instant.now().minusMillis(start.toEpochMilli());
                log.info("Download progress: {}% elapsed: {}s eta: {}s", progress, elapsed.getEpochSecond(), eta);
            }
        }
    }


}
