package yapily.marvel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarvelCharacter {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

}
