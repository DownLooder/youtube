package life.macchiato.download.utils;

import jakarta.annotation.Nullable;
import life.macchiato.common.requests.DownloadRequest;
import life.macchiato.common.responses.LoodResponse;
import life.macchiato.ytdlp.YtDlp;
import life.macchiato.ytdlp.YtDlpException;
import life.macchiato.ytdlp.YtDlpRequest;
import life.macchiato.ytdlp.YtDlpResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.OutputStream;

@Slf4j
public class Downloader {

    private final YtDlpRequest request;

    public Downloader(builder builder) {
        request = builder.request;
    }
    public static class builder {

        private final YtDlpRequest request;

        public builder(DownloadRequest downloadRequest) {
            request = new YtDlpRequest(downloadRequest.webpageUrl());
            String outDir = outDir(downloadRequest.format());
            request.setDirectory(outDir);
        }

        public Downloader build() {
            return new Downloader(this);
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

    public LoodResponse execute(@Nullable OutputStream outputStream)
    {
        YtDlpResponse response = null;
        try
        {
            response = YtDlp.execute(request, new Progress());
            return new LoodResponse();
        }
        catch (YtDlpException e)
        {
            throw new RuntimeException(e);
        }

    }

    public LoodResponse execute() {
        return execute(null);
    }


}
