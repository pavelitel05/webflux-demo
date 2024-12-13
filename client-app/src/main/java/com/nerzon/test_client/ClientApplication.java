package com.nerzon.test_client;

import com.nerzon.test_client.entity.Book;
import com.nerzon.test_client.service.RandomTextGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    private final WebClient webFluxClient;
    private final RestTemplate restApiClient;

    private int restAppBookId = 10;
    private int reactiveAppBookId = 10;

    public ClientApplication() {
        this.webFluxClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        this.restApiClient = new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        long start, finish;
        start = System.nanoTime();
        for (int i = 0; i <= 500; i++) {
            pushToReactiveApp();
        }
        for (int i = 10; i < reactiveAppBookId; i++) {
            pullFromReactiveApp(String.valueOf(i));
        }
        finish = System.nanoTime();
        System.out.println("Reactive result: " + (finish - start) / 1_000_000 + "ms");
// ---------------------------------------------------------------------------------------------------------------------
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        start = System.nanoTime();
        for (int i = 0; i <= 500; i++) {
            pushToRestApp();
        }
        for (int i = 10; i < restAppBookId; i++) {
            pullFromRestApp(String.valueOf(i));
        }
        finish = System.nanoTime();
        System.out.println("Rest result: " + (finish - start) / 1_000_000 + "ms");
    }

    private void pullFromReactiveApp(String id) {
        webFluxClient.get()
                .uri("/api/books/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("Ошибка при запросе к reactive-app: " + error.getMessage()))
                .subscribe();
    }

    private void pullFromRestApp(String id) {
        restApiClient.getForEntity("http://localhost:9090/api/books/" + id, Book.class);
    }

    private void pushToReactiveApp() {
        webFluxClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(
                        new Book(
                                String.valueOf(reactiveAppBookId++),
                                RandomTextGenerator.randomText(10),
                                RandomTextGenerator.randomText(15)
                        )
                )
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

    private void pushToRestApp() {
        restApiClient.postForEntity(
                "http://localhost:9090/api/books",
                new Book(
                        String.valueOf(reactiveAppBookId++),
                        RandomTextGenerator.randomText(10),
                        RandomTextGenerator.randomText(15)
                ),
                Book.class
        );
    }
}
