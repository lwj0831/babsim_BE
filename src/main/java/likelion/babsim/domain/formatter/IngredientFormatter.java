package likelion.babsim.domain.formatter;

import likelion.babsim.domain.ingredientLink.service.IngredientService;
import likelion.babsim.web.recipe.IngredientForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IngredientFormatter {
    private final IngredientService ingredientService;

    //"apple 1,banana 2" -> {{"name":apple,"quantity":1,"link":http://~~~},{"name":banana,"quantity":2,"link":http://~~~}}
    public List<IngredientForm> parseIngredientFormList(String ingredientsStr) {
        List<IngredientForm> IngredientFormList = new ArrayList<>();
        String[] items = ingredientsStr.split(",");// 문자열을 쉼표로 분리하여 각 항목을 가져옴
        for (String item : items) {
            String[] parts = item.trim().split(" ");// 각 항목을 공백으로 분리하여 이름과 개수를 추출
            if (parts.length == 2) {
                String name = parts[0];
                String quantity;
                try {
                    quantity = parts[1];
                } catch (NumberFormatException e) {
                    quantity = null; // 개수가 숫자가 아닌 경우 처리
                }
                String link = ingredientService.findLinkByIngredientName(name);
                IngredientForm IngredientForm = new IngredientForm(name, quantity,link);
                IngredientFormList.add(IngredientForm);
            }
        }
        return IngredientFormList;
    }

    public static String formatIngredientFormList(List<IngredientForm> ingredientFormList) {
        return ingredientFormList.stream()
                .map(ingredient -> ingredient.getName() + " " + ingredient.getQuantity())
                .collect(Collectors.joining(","));
    }
}
