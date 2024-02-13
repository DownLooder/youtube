package life.macchiato.download.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.macchiato.common.requests.DownloadRequest;
import life.macchiato.download.repositories.DownloadRepository;
import life.macchiato.download.utils.Progress;
import life.macchiato.ytdlp.YtDlp;
import life.macchiato.ytdlp.YtDlpException;
import life.macchiato.ytdlp.YtDlpRequest;
import life.macchiato.ytdlp.YtDlpResponse;
import life.macchiato.ytdlp.mapper.VideoFormat;
import life.macchiato.ytdlp.mapper.VideoInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class DownloadService {

    @Autowired
    private DownloadRepository repo;

    public YtDlpResponse requestVideo(DownloadRequest request) {

        YtDlpRequest ytdlpRequest = new YtDlpRequest(request.webpageUrl());
        ytdlpRequest.setOption("format", request.format());
        YtDlpResponse response = null;
        try {
            response = YtDlp.execute(ytdlpRequest, new Progress());
        } catch (YtDlpException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getVersion() {
        try {
            String version = YtDlp.getVersion();
            return "YoutubeDLP version: ".concat(version);
        } catch (YtDlpException e) {
            throw new RuntimeException(e);
        }
    }

    public YtDlpResponse testRequest(DownloadRequest request) {
        YtDlpRequest ytdlpRequest = new YtDlpRequest(request.webpageUrl());
//        ytdlpRequest.setOption("dump-json");
        ytdlpRequest.setOption("no-playlist");
        ytdlpRequest.setOption("restrict-filenames");
        ytdlpRequest.setOption("print", "filename");
        ytdlpRequest.setOption("format", request.format());

        String outputString = "%(title)s.%(ext)s";
        ytdlpRequest.setOption("output", outputString);

        YtDlpResponse response = null;
        try {
            response = YtDlp.execute(ytdlpRequest, new Progress());
//            ObjectMapper mapper = new ObjectMapper();
//            log.info("{}", response.getOut());

        } catch (YtDlpException e) {
            e.printStackTrace();
        }

        log.info("{}", request);

        return response;
    }

    public Set<String> videoFormats(DownloadRequest downloadRequest) {
        Set<String> formats = new HashSet<>();
        try
        {
            for (VideoFormat format : YtDlp.getFormats(downloadRequest.webpageUrl())) {
                formats.add(format.getExt());
            }

        }
        catch (YtDlpException e)
        {
            throw new RuntimeException(e);
        }

        return formats;
    }
}
