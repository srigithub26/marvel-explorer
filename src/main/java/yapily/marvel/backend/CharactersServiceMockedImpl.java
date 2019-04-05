package yapily.marvel.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import yapily.marvel.model.MarvelCharacter;

@Service
public class CharactersServiceMockedImpl implements CharactersService {

    private static final List<MarvelCharacter> repository = new ArrayList<>();

    static {
        for (Long i = 0l; i < 100l; i++) {
            repository.add(new MarvelCharacter(i,
                                               "Name " + i,
                                               "Description " + i,
                                               "https://loremflickr.com/320/240/super,hero?lock=" + i));
        }
    }

    @Override
    public List<MarvelCharacter> getCharacters(int offset, int limit) {
        if (offset < repository.size()) {
            int toIndex = offset + limit;
            if (toIndex > repository.size()) {
                toIndex = repository.size();
            }
            return repository.subList(offset, toIndex);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer countCharacters() {
        return repository.size();
    }

    @Override
    public Optional<MarvelCharacter> getCharacter(Long id) {
        return repository.stream()
                         .filter(c -> c.getId().equals(id))
                         .findAny();
    }


}
