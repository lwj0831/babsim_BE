package likelion.babsim.domain.formatter;

import java.util.ArrayList;
import java.util.List;

public class RecipeContentFormatter {
    public static List<String> parseRecipeContentList(String contentsStr) {
        List<String> contentList = new ArrayList<>();
        String[] contents = contentsStr.split("/"); // 문자열을 쉼표로 분리하여 각 content를 가져옴
        for (String content : contents) {
            content = content.trim(); // 공백 제거
            if (!content.isEmpty()) {
                contentList.add(content);
            }
        }
        return contentList;
    }
}
