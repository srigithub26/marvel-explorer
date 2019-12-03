package yapily.marvel.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.server.Resource;

import yapily.marvel.backend.CharactersService;
import yapily.marvel.model.MarvelCharacter;

@Route
@HtmlImport("frontend://styles/shared-styles.html")
public class MainView extends VerticalLayout {

    private static final int PAGE_SIZE = 25;
    private Integer position = 0;

    private VerticalLayout charactersLayout;
    private CharactersService charactersService;
    private final Grid<MarvelCharacter> grid;
    private Button loadMoreButton;
    private TextField filterText = new TextField("Filter by Name/Description");

    public MainView(@Autowired CharactersService charactersService) {
        this.charactersService = charactersService;
        this.grid = new Grid<>(MarvelCharacter.class);
        filterText.addValueChangeListener(e->{
        	
        	grid.setItems(charactersService.getCharactersByName(filterText.getValue()));
        });
        add(filterText);
        
        Grid.Column<Resource> thumbnailColumn = grid.addColumn(User::getThumbnail)
                .setCaption("Thumbnail")
                .setRenderer(new ImageRenderer<>(this::showImage))
                .setStyleGenerator(r -> "thumbnail")
                .setWidth(131);
        
        add(grid);
        charactersLayout = new VerticalLayout();
        grid.setColumns("name","description","imageUrl");
        
        grid.addComponentColumn(probe -> {
            Image image = new Image("", new ThemeResource("img/" + probe.getStructureData().getImageFileName()));
            image.setWidth(800, Sizeable.Unit.PIXELS);
            image.setHeight(800, Sizeable.Unit.PIXELS);

            return image;
        }).setCaption("Structure");
        
        loadMoreButton = new Button("Load more", l -> loadMore());
        grid.setItems(charactersService.getCharacters(position, PAGE_SIZE));
        add(charactersLayout, loadMoreButton);

        //loadMore();
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
