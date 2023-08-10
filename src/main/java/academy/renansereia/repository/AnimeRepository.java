package academy.renansereia.repository;

import academy.renansereia.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime,Long> {

    @Query("SELECT u FROM Anime u WHERE u.name like %:name% ")
    List<Anime> findByName(@Param("name") String name);
}
