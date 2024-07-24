package likelion.babsim.web.recipe;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IngredientForm {
    private String name;
    private String quantity;
    private String link;

    @Builder
    public IngredientForm(String name, String quantity, String link) {
        this.name = name;
        this.quantity = quantity;
        this.link = link;
    }
}
