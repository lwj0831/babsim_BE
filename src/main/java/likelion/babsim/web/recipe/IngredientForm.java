package likelion.babsim.web.recipe;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IngredientForm {
    private String name;
    private double quantity;
    private String link;

    public IngredientForm(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    @Builder
    public IngredientForm(String name, double quantity, String link) {
        this.name = name;
        this.quantity = quantity;
        this.link = link;
    }
}
