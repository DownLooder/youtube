package life.macchiato.fishsticks.ytdlp.services;

import com.jfposton.ytdlp.YtDlp;
import com.jfposton.ytdlp.YtDlpException;
import com.jfposton.ytdlp.mapper.VideoInfo;
import life.macchiato.fishsticks.ytdlp.controllers.requests.VideoRequest;
import life.macchiato.fishsticks.ytdlp.models.Video;
import life.macchiato.fishsticks.ytdlp.repositories.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public Video requestVideo(VideoRequest videoRequest) {
        Video video = null;
        try {
            VideoInfo v = YtDlp.getVideoInfo(videoRequest.webpageUrl());
            video = Video.builder()
                    .videoId(v.getId())
                    .title(v.getTitle())
                    .duration(v.getDuration())
                    .thumbnail(v.getThumbnail())
                    .webpageUrl(v.getWebpageUrl())
                    .build();
        } catch (YtDlpException e) {
            throw new RuntimeException(e);
        }

        videoRepository.save(video);
        return video;
    }

    public String getVersion() throws YtDlpException {
        return "YoutubeDLP version: ".concat(YtDlp.getVersion());
    }
}