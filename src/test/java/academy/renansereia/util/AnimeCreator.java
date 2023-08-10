package academy.renansereia.util;

import academy.renansereia.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Testando create anime for junit5")
                .build();
    }

    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Testando create anime for junit5")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdateAnime(){
        return Anime.builder()
                .name("Testando create anime for junit5")
                .id(1L)
                .build();
    }


}
