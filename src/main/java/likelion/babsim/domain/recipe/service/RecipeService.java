package likelion.babsim.domain.recipe.service;

import likelion.babsim.domain.allergy.AllergyType;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import likelion.babsim.domain.allergy.service.AllergyService;
import likelion.babsim.domain.category.repository.CategoryRepository;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.service.CookedRecordService;
import likelion.babsim.domain.formatter.*;
import likelion.babsim.domain.keyword.Keyword;
import likelion.babsim.domain.keyword.repository.KeywordRepository;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.likes.service.LikesService;
import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.nft.repository.NftRepository;
import likelion.babsim.domain.nft.service.KlaytnApiService;
import likelion.babsim.domain.nft.service.NftService;
import likelion.babsim.domain.recipe.*;
import likelion.babsim.domain.recipe.repository.MemberRecipeRepository;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.service.RecipeReviewService;
import likelion.babsim.domain.tag.Tag;
import likelion.babsim.domain.tag.service.TagService;
import likelion.babsim.gemini.GeminiService;
import likelion.babsim.web.recipe.RecipeCreateReqDto;
import likelion.babsim.web.recipe.RecipeCreateResDto;
import likelion.babsim.web.recipe.RecipeDetailResDto;
import likelion.babsim.web.recipe.RecipeInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final TagService tagService;
    private final RecipeReviewService recipeReviewService;
    private final AllergyService allergyService;
    private final CookedRecordService cookedRecordService;
    private final LikesService likesService;
    private final IngredientFormatter ingredientFormatter;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final MemberRecipeRepository memberRecipeRepository;
    private final KeywordRepository keywordRepository;
    private final GeminiService geminiService;
    private final RecipeAllergyRepository recipeAllergyRepository;

    public List<RecipeInfoResDto> findRecipesByKeyword(String keyword){
        Pageable pageable = PageRequest.of(0, 50);
        List<Recipe> recipes = recipeRepository.findAllByRecipeNameContaining(keyword,pageable);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findTop10RecipesByCookedCount(){
        List<Recipe> recipes = cookedRecordService.findTop10CookedRecords().stream()
                .map(CookedRecord::getRecipe)
                .toList();
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findRecommendRecipesByMemberId(String memberId){
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> allergies = allergyService.findAllergiesByMemberId(memberId);
        List<Recipe> recipes = recipeRepository.findRecipesExcludingAllergies(allergies,pageable);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findLikesRecipesByMemberId(String memberId){
        List<Recipe> recipes = likesService.findLikesByMemberId(memberId).stream()
                .map(Likes::getRecipe)
                .toList();
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findForkedRecipesByMemberId(String memberId){
        List<Recipe> recipes = recipeRepository.findByCreatorIdNotAndMemberId(memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findSpecificForkedRecipesByMemberIdAndRecipeId(String memberId, Long recipeId){
        List<Recipe> recipes = recipeRepository.findByCreatorIdNotAndMemberIdAndRecipeId(memberId,recipeId);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findMyRecipesByCreatorId(String memberId){
        List<Recipe> recipes = recipeRepository.findAllByCreatorId(memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findMyRecipesByOwnerId(String memberId){
        List<Recipe> recipes = recipeRepository.findAllByOwnerId(memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findRecipesByCategoryId(Long categoryId){
        List<Recipe> recipes;
        if(categoryId==0){  //모두보기
            recipes = recipeRepository.findRandom50Recipes();
        }
        else {
            recipes = recipeRepository.findAllByCategoryId(categoryId);
        }
        return recipesToRecipeInfoResDTOList(recipes);
    }

    private List<RecipeInfoResDto> recipesToRecipeInfoResDTOList(List<Recipe> recipes){
        List<RecipeInfoResDto> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeInfoResDto dto = RecipeInfoResDto.builder()
                    .id(recipe.getId())
                    .recipeImg(recipe.getRecipeImgs())
                    .recipeName(recipe.getRecipeName())
                    .cookingTime(recipe.getCookingTime())
                    .tags(tagService.findTagNamesByRecipeId(recipe.getId()))
                    .rate(recipeReviewService.findRatingByRecipeId(recipe.getId()))
                    .allergies(allergyService.findAllergiesByRecipeId(recipe.getId()))
                    .build();
            result.add(dto);
        }
        return result;
    }

    public RecipeDetailResDto findRecipeDetailByRecipeIdAndMemberId(Long recipeId, String memberId){
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        return recipeToRecipeDetailResDTO(recipe,recipeId,memberId);
    }

    @Transactional
    public RecipeCreateResDto createRecipe(RecipeCreateReqDto dto, String creatorId) {
        Recipe recipe = Recipe.builder()
                .creatorId(creatorId)
                .recipeImgs(String.join(",", dto.getRecipeImgs()))
                .recipeName(dto.getName())
                .recipeDescription(dto.getDescription())
                .difficulty(dto.getDifficulty())
                .cookingTime(dto.getCookingTime())
                .recipeDetailImgs(String.join(",", dto.getRecipeDetailImgs()))
                .ingredients(String.join(",",dto.getIngredients().stream().map(i->i.getName()+" "+i.getAmount()).toList()))
                .recipeContents(String.join("/",dto.getRecipeContents()))
                .timers(dto.getTimers().stream().map(String::valueOf).collect(Collectors.joining(",")))
                .category(categoryRepository.findById(dto.getCategoryId()).orElseThrow())
                .ownerId(creatorId)
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);
        System.out.println(savedRecipe.getDifficulty());

        //tag
        List<String> tagsStr = dto.getTags();
        List<String> tagNameList = new ArrayList<>();
        for (String tagStr : tagsStr) {
            Tag tag = Tag.builder()
                    .tagName(tagStr)
                    .recipe(recipe).
                    build();
            tagService.saveTag(tag);
            tagNameList.add(tag.getTagName());
        }

        //recipeAllergy
        String allergyResult = geminiService.getCompletion(recipe.getIngredients()+"In recipe there are ingredients("
                +recipe.getIngredients()+")is used and there is official allergyList("+ Arrays.toString(AllergyType.values())+
                "), choose all allergies that can be caused by eating those ingredients using English");
        System.out.println(allergyResult);

        List<String> allergyList = new ArrayList<>();
        for (AllergyType allergyType : AllergyType.values()){
            String allergyName = allergyType.getName(); //ex)알류
            if(allergyResult.contains(allergyType.toString())){ //ex)EGG
                RecipeAllergy recipeAllergy = RecipeAllergy.builder()
                        .allergy(allergyService.findAllergyByAllergyName(allergyName))
                        .recipe(recipe)
                        .build();
                recipeAllergyRepository.save(recipeAllergy);
                allergyList.add(allergyName);
            }
        }

        //nft

        //creator와 recipe연결
        MemberRecipe memberRecipe = MemberRecipe.builder()
                .recipe(recipe)
                .member(memberRepository.findById(creatorId).orElseThrow()).build();
        memberRecipeRepository.save(memberRecipe);

        //키워드 추출해서 Keyword 테이블에 넣기
        List<String> keywords = Arrays.stream(dto.getName().split(" ")).toList();
        Keyword k;
        for (String keyword : keywords) {
            if((k=keywordRepository.findByKeyword(keyword))==null){ //해당 키워드 처음
                k = Keyword.builder()
                        .count(0L)
                        .keyword(keyword)
                        .build();
                keywordRepository.save(k);
            }
            else{ //해당 키워드 이미 존재 시 count 1증가
                k.increaseCount();
                keywordRepository.save(k);
            }
        }

        return RecipeCreateResDto.builder()
                .recipeImgs(Arrays.stream(savedRecipe.getRecipeImgs().split(",")).toList())
                .name(savedRecipe.getRecipeName())
                .description(savedRecipe.getRecipeDescription())
                .difficulty(savedRecipe.getDifficulty()) //
                .cookingTime(savedRecipe.getCookingTime())
                .categoryName(savedRecipe.getCategory().getCategoryName())
                .tags(tagNameList) //getTags()시 Null
                .ingredients(ingredientFormatter.parseIngredientFormList(savedRecipe.getIngredients()))
                .recipeContents(Arrays.stream(savedRecipe.getRecipeContents().split("/")).toList())
                .recipeDetailImgs(Arrays.stream(savedRecipe.getRecipeDetailImgs().split(",")).toList())
                .timers(Arrays.stream(savedRecipe.getTimers().split(",")).map(Integer::parseInt).toList())
                .allergyList(allergyList)
                .build();
    }

    @Transactional
    public RecipeCreateResDto editRecipe(RecipeCreateReqDto dto, String creatorId,Long recipeId) {
        Optional<Recipe> findRecipe = recipeRepository.findById(recipeId);
        if(findRecipe.isPresent()) {
            Recipe recipe = findRecipe.get();
            recipeRepository.delete(recipe);
            return createRecipe(dto, creatorId);
        }
        else return null;
    }

    private RecipeDetailResDto recipeToRecipeDetailResDTO(Recipe recipe, Long recipeId, String memberId) {
        return RecipeDetailResDto.builder()
                .id(recipe.getId())
                .creatorId(recipe.getCreatorId())
                .recipeImgs(RecipeImgFormatter.parseImageIdList(recipe.getRecipeImgs()))//
                .name(recipe.getRecipeName())
                .description(recipe.getRecipeDescription())
                .rate(recipeReviewService.findRatingByRecipeId(recipe.getId()))//
                .difficulty(recipe.getDifficulty())
                .cookingTime(recipe.getCookingTime())
                .allergies(allergyService.findAllergiesByRecipeId(recipe.getId()))//
                .tags(tagService.findTagNamesByRecipeId(recipe.getId()))
                .ingredients(ingredientFormatter.parseIngredientFormList(recipe.getIngredients()))//
                .reviews(recipeReviewService.findReviewsByRecipeId(recipeId))//
                .reviewsCount(recipeReviewService.findReviewsCount(recipeId))//
                .recipeDetailImgs(RecipeDetailImgFormatter.parseRecipeDetailIdList(recipe.getRecipeDetailImgs()))//
                .recipeContents(RecipeContentFormatter.parseRecipeContentList(recipe.getRecipeContents()))//
                .recipeTimers(RecipeTimerFormatter.parseTimerList(recipe.getTimers()))//
                .liked(likesService.checkLikesByMemberIdAndRecipeId(memberId, recipeId))//
                .createdNft(recipe.getNft() != null)
                .categoryName(categoryRepository.findById(recipe.getCategory().getId()).orElseThrow().getCategoryName())
                .build();
    }
}
