package yapily.marvel.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import yapily.marvel.backend.CharactersService;

@Route("character")
public class CharacterView extends VerticalLayout implements HasUrlParameter<Long> {

    private Image image;
    private CharactersService charactersService;


    public CharacterView(@Autowired CharactersService charactersService) {
        this.charactersService = charactersService;
        NativeButton button = new NativeButton("Back");
        button.addClickListener(e -> {
            button.getUI().ifPresent(ui -> ui.navigate(MainView.class));
        });

        image = new Image();
        add(button, image);
    }


    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        charactersService.getCharacter(parameter)
                         .ifPresent(c -> image.setSrc(c.getImageUrl()));
    }

}
