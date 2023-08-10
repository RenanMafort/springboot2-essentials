package academy.renansereia.client;

import academy.renansereia.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {

        ResponseEntity<Anime> forEntity = new RestTemplate().getForEntity("http://localhost:8080/animes/2", Anime.class);
        log.info(forEntity);
        log.info(forEntity.getStatusCode());
        log.info(forEntity.getHeaders());
        log.info(forEntity.getBody());

        Anime[] forObject = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(forObject));

        ResponseEntity<List<Anime>> forObjectList = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        log.info(forObjectList);

        //post

//        Anime anime = Anime.builder().name("Renan Teste post Client").build();
//        Anime animeSaved = new RestTemplate().postForObject("http://localhost:8080/animes", anime, Anime.class);
//        log.info(animeSaved);

        Anime anime = Anime.builder().name("Renan Teste post exchange").build();
        ResponseEntity<Anime> exchange = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.POST, new HttpEntity<>(anime,createJsonHeader()), Anime.class);
        log.info(exchange);

        Anime body = exchange.getBody();
        body.setName("Test put");

        ResponseEntity<Void> exchangePut = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.PUT, new HttpEntity<>(anime, createJsonHeader()), Void.class);
        log.info(exchangePut);

        ResponseEntity<Void> exchangeDelet = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE, null, Void.class,body.getId());
        log.info(exchangePut);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
