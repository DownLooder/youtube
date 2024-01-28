package life.macchiato.fishsticks.services;

import com.jfposton.ytdlp.YtDlp;
import com.jfposton.ytdlp.YtDlpException;
import com.jfposton.ytdlp.mapper.VideoInfo;
import life.macchiato.fishsticks.controllers.requests.VideoRequest;
import life.macchiato.fishsticks.models.Video;
import org.springframework.stereotype.Service;

@Service
public record VideoService() {
    public Video requestVideo(VideoRequest videoRequest) {
        Video video = null;
        try {
            VideoInfo v = YtDlp.getVideoInfo(videoRequest.webpageUrl());
            video = Video.builder()
                    .videoId(v.getId())
                    .duration(v.getDuration())
                    .thumbnail(v.getThumbnail())
                    .webpageUrl(v.getWebpageUrl())
                    .build();
        } catch (YtDlpException e) {
            throw new RuntimeException(e);
        }

        return video;
    }

    public String getVersion() throws YtDlpException {
        return "YoutubeDLP version: ".concat(YtDlp.getVersion());
    }
}
