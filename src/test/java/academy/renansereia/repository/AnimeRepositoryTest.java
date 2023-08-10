package academy.renansereia.repository;

import academy.renansereia.domain.Anime;
import academy.renansereia.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when succesful")
    public void save_PersistAnime_WhenSuccesful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();

        Assertions.assertThat(animeSaved.getId()).isNotNull();

        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when succesful")
    public void save_UpdateAnime_WhenSuccesful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Overload");

        Anime animeUpdate = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdate).isNotNull();

        Assertions.assertThat(animeUpdate.getId()).isNotNull();

        Assertions.assertThat(animeUpdate.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when succesful")
    public void delete_RemovesAnime_WhenSuccesful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> byId = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(byId).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of  anime when succesful")
    public void findByName_ReturnsListOfAnime_WhenSuccesful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> ListbyName = this.animeRepository.findByName(name);

        Assertions.assertThat(ListbyName).isNotEmpty();

        Assertions.assertThat(ListbyName).contains(animeSaved);

    }

    @Test
    @DisplayName("Find by name returns empty list when no anime is found")
    public void findByName_ReturnsEmptyList_WhenAnimeIsFound(){
        List<Anime> animes = this.animeRepository.findByName("teste");

        Assertions.assertThat(animes).isEmpty();

    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    public void save_ConstraintViolationException_WhenNameIsEmpty(){
        Anime animeToBeSaved = new Anime();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(animeToBeSaved)).isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(animeToBeSaved))
                .withMessageContaining("The anime name cannot be empty");

    }



}