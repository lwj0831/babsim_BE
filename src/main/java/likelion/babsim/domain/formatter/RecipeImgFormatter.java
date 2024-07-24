package likelion.babsim.domain.formatter;

import java.util.ArrayList;
import java.util.List;

public class RecipeImgFormatter {
    public static List<String> parseImageUrlList(String urlsStr) {
        List<String> urlList = new ArrayList<>();
        String[] urls = urlsStr.split(","); // 문자열을 쉼표로 분리하여 각 URL을 가져옴
        for (String url : urls) {
            url = url.trim(); // 공백 제거
            if (!url.isEmpty()) {
                urlList.add(url);
            }
        }
        return urlList;
    }
}
