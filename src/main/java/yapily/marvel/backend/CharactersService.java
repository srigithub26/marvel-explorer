package yapily.marvel.backend;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import yapily.marvel.model.MarvelCharacter;

public interface CharactersService {

	List<MarvelCharacter> getAllCharacters();
    List<MarvelCharacter> getCharacters(int offset, int limit);

    Integer countCharacters();

    Optional<MarvelCharacter> getCharacter(Long id);
    Collection<MarvelCharacter> getCharactersByName(String name);

}
