package likelion.babsim;

import likelion.babsim.domain.recipe.Difficulty;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MultiUrlCrawler {

    @Data
    static class RecipeDummy {
        private static int userIdCounter = 1;
        private static long categoryIdCounter = 1;
        private static long memberIdCounter = 1;
        private static int memberTemp = 0;
        private static int categoryTemp=0;
        String id;
        String creatorId;
        String recipeImg;
        String recipeName;
        String description;
        Difficulty difficulty;
        String cookingTime;
        List<String> recipeDetailImgs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        List<String> recipeContents = new ArrayList<>();
        List<Integer> timers = new ArrayList<>();
        List<String> ingredientsLink = new ArrayList<>();
        String ownerId;
        Long categoryId;

        @Override
        public String toString() {
            return "(" + id + ", '" + creatorId + "', '" + recipeImg + "', '" + recipeName + "', '"
                    + description + "', '" + difficulty + "', " + cookingTime + ", '"
                    + String.join(",", recipeDetailImgs) + "', '"
                    + String.join(",", ingredients) + "', '"
                    + String.join("/", recipeContents) + "', '"
                    + String.join(",", timers.stream().map(String::valueOf).collect(Collectors.joining(","))) + "', '"
                    + ownerId + "', " + categoryId + "),";
        }
    }
    public static void main(String[] args) {
        List<String> urls = List.of(
                "https://m.10000recipe.com/recipe/6965365",
                "https://m.10000recipe.com/recipe/6903507",
                "https://m.10000recipe.com/recipe/7022775",
                "https://m.10000recipe.com/recipe/7027008",
                "https://m.10000recipe.com/recipe/7030528",
                "https://m.10000recipe.com/recipe/6992063",//메인요리
                "https://m.10000recipe.com/recipe/6877253",
                "https://m.10000recipe.com/recipe/6979678",
                "https://m.10000recipe.com/recipe/6933414",
                "https://m.10000recipe.com/recipe/6842114",
                "https://m.10000recipe.com/recipe/6834258",
                "https://m.10000recipe.com/recipe/6984728",//단순요리
                "https://m.10000recipe.com/recipe/6866122",
                "https://m.10000recipe.com/recipe/6933349",
                "https://m.10000recipe.com/recipe/6907091",
                "https://m.10000recipe.com/recipe/6911766",
                "https://m.10000recipe.com/recipe/6833230",
                "https://m.10000recipe.com/recipe/6871193",//비건  //쪽파무침 오류
                "https://m.10000recipe.com/recipe/4187342",
                "https://m.10000recipe.com/recipe/6720605",
                "https://m.10000recipe.com/recipe/6406162",
                "https://m.10000recipe.com/recipe/6194344",
                "https://m.10000recipe.com/recipe/6404903",
                "https://m.10000recipe.com/recipe/3149743",//스낵
                "https://m.10000recipe.com/recipe/6926078",
                "https://m.10000recipe.com/recipe/6860598",
                "https://m.10000recipe.com/recipe/6847733",
                "https://m.10000recipe.com/recipe/6847230",
                "https://m.10000recipe.com/recipe/6622543",
                "https://m.10000recipe.com/recipe/1607382",//베이킹
                "https://m.10000recipe.com/recipe/6832722",
                "https://m.10000recipe.com/recipe/6769316",
                "https://m.10000recipe.com/recipe/6403123",
                "https://m.10000recipe.com/recipe/6754761",
                "https://m.10000recipe.com/recipe/6768283",
                "https://m.10000recipe.com/recipe/3427688",//다이어트
                "https://m.10000recipe.com/recipe/6880131",
                "https://m.10000recipe.com/recipe/6842570",
                "https://m.10000recipe.com/recipe/6832851",
                "https://m.10000recipe.com/recipe/6830337",
                "https://m.10000recipe.com/recipe/6832958",
                "https://m.10000recipe.com/recipe/6896162",//오븐
                "https://m.10000recipe.com/recipe/6933974",
                "https://m.10000recipe.com/recipe/6986101",
                "https://m.10000recipe.com/recipe/6986865",
                "https://m.10000recipe.com/recipe/6950154",
                "https://m.10000recipe.com/recipe/7005441",
                "https://m.10000recipe.com/recipe/7011834"//키토
        );
        List<RecipeDummy> recipes = urls.stream()
                .map(MultiUrlCrawler::fetchRecipeFromUrl)
                .filter(recipe -> recipe != null) // null 체크
                .toList();

        recipes.forEach(System.out::println);
    }

    private static RecipeDummy fetchRecipeFromUrl(String urlString) {
        String line;

        try {
            URL url = new URI(urlString).toURL();
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader inReader = new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8);
                     BufferedReader reader = new BufferedReader(inReader)) {

                    RecipeDummy recipeDummy = new RecipeDummy();
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("view3_pic_img")) {
                            recipeDummy.setRecipeImg(line.substring(line.indexOf("img src=") + 9, line.indexOf("</div>") - 2));
                        }
                        if (line.contains("view3_top_tit")) {
                            recipeDummy.setRecipeName(line.substring(line.indexOf("view3_top_tit") + 15, line.indexOf("</div>")));
                        }
                        if (line.contains("view3_top_summary")) {
                            String description = line.substring(line.indexOf("view3_top_summary") + 19, line.indexOf("</div>"));
                            recipeDummy.setDescription(description.replaceAll("<p>", "").replaceAll("</p>", ""));
                        }
                        if (line.contains("view3_top_info")) {
                            line = reader.readLine();
                            recipeDummy.setCookingTime(extractCookingTime(line));
                            recipeDummy.setDifficulty(extractLevel(line));
                        }
                        if (line.contains("step_list_txt_cont")) {
                            if (line.contains("/div></li>")) {
                                recipeDummy.getRecipeDetailImgs().add(line.substring(line.indexOf("img src") + 9, line.indexOf("</div></li>") - 2));
                                recipeDummy.getRecipeContents().add(line.substring(line.indexOf("step_list_txt_cont") + 20, line.indexOf("</div>")));
                            } else if (line.contains("<br")) {
                                recipeDummy.getRecipeContents().add(line.substring(line.indexOf("step_list_txt_cont") + 20, line.indexOf("<br")));
                                while (!line.contains("img src")) line = reader.readLine();
                                recipeDummy.getRecipeDetailImgs().add(line.substring(line.indexOf("img src") + 9, line.indexOf("</div></li>") - 2));
                            }
                        }
                        if (line.contains("ingre_list_name") && !line.contains("view3_box_tit")) {
                            String ingredient;
                            line = reader.readLine();
                            line = reader.readLine();
                            ingredient = line.replaceAll(" ", "").replaceAll("</a>", "");
                            while (!line.contains("ingre_list_ea")) line = reader.readLine();
                            ingredient = ingredient.concat(" " + line.substring(line.indexOf("ingre_list_ea") + 15, line.indexOf("</span>")));
                            recipeDummy.getIngredients().add(ingredient);
                        }
                    }
                    recipeDummy.setId(String.valueOf(RecipeDummy.userIdCounter++));
                    recipeDummy.setCreatorId(String.valueOf(RecipeDummy.memberIdCounter));
                    recipeDummy.setOwnerId(recipeDummy.getCreatorId());
                    recipeDummy.setTimers(getRandomNumbers(recipeDummy.getRecipeContents().size()));
                   /* Collections.nCopies(recipeDummy.getRecipeContents().size(), 0);*/
                    recipeDummy.setCategoryId(RecipeDummy.categoryIdCounter);
                    if(++RecipeDummy.categoryTemp==6){
                        RecipeDummy.categoryTemp=0;
                        RecipeDummy.categoryIdCounter++;
                    }
                    if(++RecipeDummy.memberTemp==10){
                        RecipeDummy.memberTemp=0;
                        RecipeDummy.memberIdCounter++;
                    }


                    return recipeDummy;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 오류 발생 시 null 반환
    }

    public static Difficulty extractLevel(String htmlString) {
        Document doc = Jsoup.parse(htmlString);
        Elements spans = doc.select("span");

        for (Element span : spans) {
            String text = span.text();
            if (text.contains("아무나")||text.contains("초급")) {
                return Difficulty.EASY;
            }
            else if(text.contains("중급")) return Difficulty.MEDIUM;
        }
        return Difficulty.HARD;
    }
    public static String extractCookingTime(String htmlString) {
        Document doc = Jsoup.parse(htmlString);
        Elements spans = doc.select("span");
        String cookingTime = null;

        for (Element span : spans) {
            String text = span.text();
            if (text.contains("분 이내")) {
                cookingTime = text.replaceAll("\\D", ""); // 숫자만 추출
                break; // 찾았으므로 루프 종료
            }
        }
        return cookingTime;
    }
    public static List<Integer> getRandomNumbers(int n) {
        // 모든 가능한 숫자를 생성
        List<Integer> numbers = new ArrayList<>();
        for (int i = 10; i <= 180; i += 10) {
            numbers.add(i);
        }

        // 리스트를 무작위로 섞음
        Collections.shuffle(numbers, new Random());

        // 앞에서 n개의 숫자를 선택
        return numbers.subList(0, n);
    }
}
