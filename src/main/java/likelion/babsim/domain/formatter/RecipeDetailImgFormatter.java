package likelion.babsim.domain.formatter;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailImgFormatter {
    public static List<String> parseRecipeDetailIdList(String idsStr) {
        List<String> idList = new ArrayList<>();
        String[] ids = idsStr.split(","); // 문자열을 쉼표로 분리하여 각 URL을 가져옴
        for (String id : ids) {
            id = id.trim(); // 공백 제거
            if (!id.isEmpty()) {
                idList.add(id);
            }
        }
        return idList;
    }
}
