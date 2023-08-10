package academy.renansereia.controller;

import academy.renansereia.domain.Anime;
import academy.renansereia.requests.AnimePostRequestBody;
import academy.renansereia.requests.AnimePutRequestBody;
import academy.renansereia.service.AnimeService;
import academy.renansereia.util.AnimeCreator;
import academy.renansereia.util.AnimePostRequestBodyCreator;
import academy.renansereia.util.AnimePutRequestBodyCreator;
import academy.renansereia.util.DateUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeService;

    @Mock
    private DateUtil dateUtil;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeService.listAll(ArgumentMatchers.any())).thenReturn(animePage);
        BDDMockito.when(animeService.listAllNoPageable()).thenReturn(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeService.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeService.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeService).replace(ArgumentMatchers.any(AnimePutRequestBody.class));
        BDDMockito.doNothing().when(animeService).delete(ArgumentMatchers.anyLong());
    }


    @Test
    @DisplayName( "List returns lists of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccesFul(){
        String name = AnimeCreator.createValidAnime().getName();
        Page<Anime> body = animeController.list(null).getBody();

        Assertions.assertThat(body).isNotNull();

        Assertions.assertThat(body.toList()).isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(body.toList().get(0).getName()).isEqualTo(name);

    }

    @Test
    @DisplayName( "ListAll returns lists of anime when successful")
    void list_ReturnsListOfAnimes_WhenSuccesFul(){
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> body = animeController.listAll().getBody();

        Assertions.assertThat(body).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(body.get(0).getName()).isEqualTo(name);

    }

    @Test
    @DisplayName( "findById returns lists of anime when successful")
    void findById_ReturnsListOfAnimes_WhenSuccesFul(){
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime body = animeController.findById(1).getBody();

        Assertions.assertThat(body).isNotNull();

        Assertions.assertThat(body.getId()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName( "findByName returns lists of anime when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccesFul(){
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> body = animeController.findByName("Teste").getBody();

        Assertions.assertThat(body).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(body.get(0).getName()).isEqualTo(name);

    }

    @Test
    @DisplayName( "findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsAnListOfAnimes_WhenAnimeIsNotFound(){
        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> body = animeController.findByName("Teste").getBody();

        Assertions.assertThat(body).isNotNull()
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName( "save returns lists of anime when successful")
    void save_ReturnAnime_WhenSuccesFul(){
        Anime body = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(body).isNotNull();

        Assertions.assertThat(body).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName( "replace updates anime when successful")
    void replace(){
         Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                 .doesNotThrowAnyException();

        ResponseEntity<Void> replace = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(replace).isNotNull();
        Assertions.assertThat(replace.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName( "delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccesFul(){
        Assertions.assertThatCode(() -> animeController.delete(1L)).doesNotThrowAnyException();

        ResponseEntity<Void> replace = animeController.delete(1L);

        Assertions.assertThat(replace).isNotNull();
        Assertions.assertThat(replace.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}