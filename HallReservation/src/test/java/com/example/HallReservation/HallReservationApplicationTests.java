package com.example.HallReservation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class HallReservationApplicationTests {

	@Test
	void contextLoads() {
	}

    @Bean
    public TestRestTemplate testRestTemplate(RestTemplateBuilder builder) {

        return new TestRestTemplate(builder);
    }

}
