package likelion.babsim.domain.formatter;

import java.util.ArrayList;
import java.util.List;

public class RecipeTimerFormatter {
    public static List<Integer> parseTimerList(String timersStr) {
        List<Integer> timerList = new ArrayList<>();
        String[] timers = timersStr.split(","); // 문자열을 쉼표로 분리하여 각 timer을 가져옴
        for (String timer : timers) {
            timer = timer.trim(); // 공백 제거
            if (!timer.isEmpty()) {
                timerList.add(Integer.parseInt(timer));
            }
        }
        return timerList;
    }
}
