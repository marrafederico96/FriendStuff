package it.federico.friendstuff;

import it.federico.friendstuff.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class FriendStuffApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendStuffApplication.class, args);
    }

}
