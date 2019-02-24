package livestream.configuration;

import com.github.seratch.jslack.Slack;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public Slack slack() {
        return Slack.getInstance();
    }
}
