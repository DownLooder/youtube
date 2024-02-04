package life.macchiato.ytdlp.services;

import com.jfposton.ytdlp.YtDlp;
import com.jfposton.ytdlp.YtDlpException;
import com.jfposton.ytdlp.mapper.VideoFormat;
import life.macchiato.common.requests.VideoRequest;
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

    public void requestVideo(VideoRequest request) {

        Looder looder = new Looder.builder(request).build();
        looder.execute();

//        videoRepository.save(video);
    }

    public String getVersion() throws YtDlpException {
        return "YoutubeDLP version: ".concat(YtDlp.getVersion());
    }

    public Set<String> videoFormats(VideoRequest videoRequest) {
        Set<String> formats = new HashSet<>();
        try
        {
            for (VideoFormat format : YtDlp.getFormats(videoRequest.webpageUrl())) {
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
