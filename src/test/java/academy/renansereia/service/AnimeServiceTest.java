package academy.renansereia.service;

import academy.renansereia.domain.Anime;
import academy.renansereia.repository.AnimeRepository;
import academy.renansereia.util.AnimeCreator;
import academy.renansereia.util.AnimePostRequestBodyCreator;
import academy.renansereia.util.AnimePutRequestBodyCreator;
import academy.renansereia.util.DateUtil;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;

    @Mock
    private DateUtil dateUtil;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(animePage);
        BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }


    @Test
    @DisplayName( "listAll returns lists of anime inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccesFul(){
        String name = AnimeCreator.createValidAnime().getName();
        Page<Anime> body = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(body).isNotNull();

        Assertions.assertThat(body.toList()).isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(body.toList().get(0).getName()).isEqualTo(name);

    }

    @Test
    @DisplayName( "ListAll returns lists of anime when successful")
    void list_ReturnsListOfAnimes_WhenSuccesFul(){
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> body = animeService.listAllNoPageable();

        Assertions.assertThat(body).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(body.get(0).getName()).isEqualTo(name);

    }

    @Test
    @DisplayName( "findByIdOrThrowBadRequestException returns lists of anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsListOfAnimes_WhenSuccesFul(){
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime body = animeService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(body).isNotNull();

        Assertions.assertThat(body.getId()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName( "findByIdOrThrowBadRequestException returns lists of anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsListOfAnimes_WhenAnimeIsNoutfOUND(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() ->  animeService.findByIdOrThrowBadRequestException(1));


    }

    @Test
    @DisplayName( "findByName returns lists of anime when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccesFul(){
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> body = animeService.findByName("Teste");

        Assertions.assertThat(body).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(body.get(0).getName()).isEqualTo(name);

    }

    @Test
    @DisplayName( "findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsAnListOfAnimes_WhenAnimeIsNotFound(){
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> body = animeService.findByName("Teste");

        Assertions.assertThat(body).isNotNull()
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName( "save returns lists of anime when successful")
    void save_ReturnAnime_WhenSuccesFul(){
        Anime body = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(body).isNotNull();

        Assertions.assertThat(body).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName( "replace updates anime when successful")
    void replace(){
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName( "delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccesFul(){
        Assertions.assertThatCode(() -> animeService.delete(1L)).doesNotThrowAnyException();



    }

}