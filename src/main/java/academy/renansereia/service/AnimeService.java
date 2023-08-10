package academy.renansereia.service;

import academy.renansereia.domain.Anime;
import academy.renansereia.exception.BadRequestException;
import academy.renansereia.mapper.AnimeMapper;
import academy.renansereia.repository.AnimeRepository;
import academy.renansereia.requests.AnimePostRequestBody;
import academy.renansereia.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;
    public Page<Anime> listAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }

    public List<Anime> listAllNoPageable() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }
    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime ID not found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody){
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id){
       animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime byIdOrThrowBadRequestException = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(byIdOrThrowBadRequestException.getId());


        animeRepository.save(anime);
    }


}
