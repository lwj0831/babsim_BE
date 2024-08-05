package likelion.babsim.gemini;

import likelion.babsim.domain.allergy.AllergyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class GeminiServiceTest {
    @Autowired
    private GeminiService geminiService;

    @Test
    void getCompletion() {
        String text = geminiService.getCompletion("서울 맛집을 추천해줘");
        System.out.println(text);
    }

    @Test
    void testGeminiAllergyResult(){
        System.out.println(Arrays.toString(AllergyType.values()));
        //String result =geminiService.getCompletion("레시피의 재료로 닭고기 300g,양파 1/2개,계란 2개,밥 2인분,표고버섯 2~3개,진간장 3T,새우 2마리,설탕 1.5~2T,맛술 1T,굴소스 1T,꿀 1/2T,고춧가루 1t 가 사용될 때,"+Arrays.toString(AllergyType.values())+"에 있는 알레르기 중에 어떤 것들이 있는지 알려줘");
        //System.out.println(result);
        //String geminiAllergyResult = geminiService.getCompletion("레시피의 재료로 닭고기 300g,양파 1/2개,계란 2개,밥 2인분, 오징어 100g,소고기 60g들이 사용될 때 알레르기 리스트("+ Arrays.toString(AllergyType.values())+")중 레시피로 유발될 수 있는 알레르기명 알려줘");
        //System.out.println(geminiAllergyResult);
        AllergyType[] values = AllergyType.values();
        for (AllergyType value : values) {
            System.out.println(value);
            System.out.println(value.toString());
            System.out.println(value.getName());
        }
    }
}