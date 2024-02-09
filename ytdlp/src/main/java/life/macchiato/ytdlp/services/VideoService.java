package life.macchiato.ytdlp.services;

import com.jfposton.ytdlp.YtDlp;
import com.jfposton.ytdlp.YtDlpException;
import com.jfposton.ytdlp.mapper.VideoFormat;
import life.macchiato.common.requests.DownloadRequest;
import life.macchiato.common.responses.LoodResponse;
import life.macchiato.ytdlp.repositories.VideoRepository;
import life.macchiato.ytdlp.utils.Looder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public LoodResponse requestVideo(DownloadRequest request) {

        Looder looder = new Looder.builder(request).build();
        LoodResponse response = looder.execute();

//        videoRepository.save(video);
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
