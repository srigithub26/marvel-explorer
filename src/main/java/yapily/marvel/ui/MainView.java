package yapily.marvel.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import yapily.marvel.backend.CharactersService;
import yapily.marvel.model.MarvelCharacter;

@Route
@HtmlImport("frontend://styles/shared-styles.html")
public class MainView extends VerticalLayout {

    private static final int PAGE_SIZE = 25;
    private Integer position = 0;

    private VerticalLayout charactersLayout;
    private CharactersService charactersService;
    private Button loadMoreButton;

    public MainView(@Autowired CharactersService charactersService) {
        this.charactersService = charactersService;

        charactersLayout = new VerticalLayout();

        loadMoreButton = new Button("Load more", l -> loadMore());

        add(charactersLayout, loadMoreButton);

        loadMore();
    }

    private void loadMore() {
        List<MarvelCharacter> characters = charactersService.getCharacters(position, PAGE_SIZE);
        characters.stream()
                  .map(this::buildCharacterComponent)
                  .forEachOrdered(charactersLayout::add);

        position += characters.size();
        if (position >= charactersService.countCharacters()) {
            loadMoreButton.setVisible(false);
        }

    }

    private Div buildCharacterComponent(MarvelCharacter character) {
        Div div = new Div();
        div.addClassNames("character", "list-item");

        Label name = new Label(character.getName());

        RouterLink button = new RouterLink("View",
                                           CharacterView.class,
                                           character.getId());
        div.add(name, button);
        return div;
    }

}
