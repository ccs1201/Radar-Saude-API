package br.com.css.radarsaude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootApplication
public class RadarSaudeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadarSaudeApiApplication.class, args);
    }

}
