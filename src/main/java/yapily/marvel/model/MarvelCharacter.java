package yapily.marvel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class MarvelCharacter {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

}
