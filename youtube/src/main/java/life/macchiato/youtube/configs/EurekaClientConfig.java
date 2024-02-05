package life.macchiato.youtube.configs;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaClientConfig {

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        config.setHostname("localhost"); // Set your hostname
        config.setPreferIpAddress(true);
        return config;
    }
}
