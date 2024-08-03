package likelion.babsim.domain.recipe.service;

import likelion.babsim.domain.allergy.AllergyType;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import likelion.babsim.domain.allergy.service.AllergyService;
import likelion.babsim.domain.category.Category;
import likelion.babsim.domain.category.repository.CategoryRepository;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.service.CookedRecordService;
import likelion.babsim.domain.formatter.*;
import likelion.babsim.domain.keyword.Keyword;
import likelion.babsim.domain.keyword.repository.KeywordRepository;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.likes.service.LikesService;
import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.nft.repository.NftRepository;
import likelion.babsim.domain.nft.repository.SaleNftRepository;
import likelion.babsim.domain.recipe.*;
import likelion.babsim.domain.recipe.repository.MemberRecipeRepository;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.service.RecipeReviewService;
import likelion.babsim.domain.tag.Tag;
import likelion.babsim.domain.tag.repository.TagRepository;
import likelion.babsim.domain.tag.service.TagService;
import likelion.babsim.gemini.GeminiService;
import likelion.babsim.web.recipe.RecipeCreateReqDto;
import likelion.babsim.web.recipe.RecipeCreateResDto;
import likelion.babsim.web.recipe.RecipeDetailResDto;
import likelion.babsim.web.recipe.RecipeInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final TagRepository tagRepository;
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
    private final NftRepository nftRepository;
    private final SaleNftRepository saleNftRepository;

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
        Pageable pageable = PageRequest.of(0, 12);
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

    public List<RecipeInfoResDto> findMyRecipesByOwnerId(String memberId){ //nft소유자 기준
        List<Nft> nfts = nftRepository.findAllByOwnerId(memberId);
        List<Recipe> recipes = new ArrayList<>();
        for (Nft nft : nfts) {
            Recipe findRecipe = recipeRepository.findByNft(nft);
            recipes.add(findRecipe);
        }
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
                .nftCreateStatus(recipe.getNft() != null)
                .nftSaleStatus(recipe.getNft() != null && saleNftRepository.findByNft(recipe.getNft()).isPresent())
                .nftOwnerId(recipe.getNft() != null ? nftRepository.findByRecipeId(recipeId).getOwnerId() : null)
                .categoryName(categoryRepository.findById(recipe.getCategory().getId()).orElseThrow().getCategoryName())
                .build();
    }

    @Transactional
    public RecipeCreateResDto createRecipe(RecipeCreateReqDto dto, String creatorId) {
        return generateRecipe(dto,creatorId,creatorId);
    }

    @Transactional
    public RecipeCreateResDto editRecipe(RecipeCreateReqDto dto, Long editedRecipeId) {
        Optional<Recipe> findRecipe = recipeRepository.findById(editedRecipeId);
        if(findRecipe.isPresent()) {
            Recipe existingRecipe = findRecipe.get();
            String recipeImgs = String.join(",", dto.getRecipeImgs());
            String recipeName = dto.getName();
            String description = dto.getDescription();
            Difficulty difficulty = dto.getDifficulty();
            Integer cookingTime = dto.getCookingTime();
            String recipeDetailImgs = String.join(",", dto.getRecipeDetailImgs());
            String ingredients = String.join(",", dto.getIngredients().stream().map(i -> i.getName() + " " + i.getAmount()).toList());
            String recipeContents = String.join("/", dto.getRecipeContents());
            String timers = dto.getTimers().stream().map(String::valueOf).collect(Collectors.joining(","));
            Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();

            // 레시피 업데이트
            existingRecipe.updateRecipeInfo(recipeImgs, recipeName, description, difficulty, cookingTime, recipeDetailImgs, ingredients, recipeContents, timers, category);

            // 태그 업데이트
            List<Tag> tags = dto.getTags().stream()
                    .map(tagStr -> new Tag(existingRecipe, tagStr))
                    .collect(Collectors.toList());
            tagRepository.deleteByRecipe(existingRecipe); // 기존 태그 삭제
            tagRepository.saveAll(tags); // 새로운 태그 저장
            existingRecipe.updateTags(tags);

            // 알레르기 정보 업데이트
            String allergyResult = geminiService.getCompletion("In recipe there are ingredients(" + dto.getIngredients() +
                    ")is used and there is official allergyList(" + Arrays.toString(AllergyType.values()) +
                    "), choose all allergies that can be caused by eating those ingredients using English");

            List<RecipeAllergy> recipeAllergies = Arrays.stream(AllergyType.values())
                    .filter(allergyType -> allergyResult.contains(allergyType.toString()))
                    .map(allergyType -> new RecipeAllergy(existingRecipe, allergyService.findAllergyByAllergyName(allergyType.getName())))
                    .collect(Collectors.toList());
            recipeAllergyRepository.deleteByRecipe(existingRecipe); // 기존 알레르기 정보 삭제
            recipeAllergyRepository.saveAll(recipeAllergies); // 새로운 알레르기 정보 저장
            existingRecipe.updateAllergies(recipeAllergies);

            Recipe updatedRecipe = recipeRepository.save(existingRecipe);

            return RecipeCreateResDto.builder()
                    .recipeImgs(Arrays.asList(updatedRecipe.getRecipeImgs().split(",")))
                    .name(updatedRecipe.getRecipeName())
                    .description(updatedRecipe.getRecipeDescription())
                    .difficulty(updatedRecipe.getDifficulty())
                    .cookingTime(updatedRecipe.getCookingTime())
                    .categoryName(updatedRecipe.getCategory().getCategoryName())
                    .tags(tags.stream().map(Tag::getTagName).collect(Collectors.toList()))
                    .ingredients(ingredientFormatter.parseIngredientFormList(updatedRecipe.getIngredients()))
                    .recipeContents(Arrays.asList(updatedRecipe.getRecipeContents().split("/")))
                    .recipeDetailImgs(Arrays.asList(updatedRecipe.getRecipeDetailImgs().split(",")))
                    .timers(Arrays.stream(updatedRecipe.getTimers().split(",")).map(Integer::parseInt).collect(Collectors.toList()))
                    .allergyList(recipeAllergies.stream().map(ra -> ra.getAllergy().getAllergyName()).collect(Collectors.toList()))
                    .build();
        }
        else throw new EmptyResultDataAccessException(1);
    }

    @Transactional
    public RecipeCreateResDto forkRecipe(RecipeCreateReqDto dto, String creatorId, Long forkedRecipeId) {
        String originalCreatorId = recipeRepository.findById(forkedRecipeId).orElseThrow().getCreatorId();
        return generateRecipe(dto,originalCreatorId,creatorId);
    }

    private RecipeCreateResDto generateRecipe(RecipeCreateReqDto dto, String originalCreatorId, String creatorId){
        Recipe recipe = Recipe.builder()
                .creatorId(originalCreatorId) //
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
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);

        //tags
        List<Tag> tags = dto.getTags().stream()
                .map(tagStr -> new Tag(savedRecipe, tagStr))
                .collect(Collectors.toList());
        tagRepository.saveAll(tags); // 새로운 태그 저장

        //recipeAllergy
        String allergyResult = geminiService.getCompletion("In recipe there are ingredients(" + dto.getIngredients() +
                ")is used and there is official allergyList(" + Arrays.toString(AllergyType.values()) +
                "), choose all allergies that can be caused by eating those ingredients using English");

        List<RecipeAllergy> recipeAllergies = Arrays.stream(AllergyType.values())
                .filter(allergyType -> allergyResult.contains(allergyType.toString()))
                .map(allergyType -> new RecipeAllergy(savedRecipe, allergyService.findAllergyByAllergyName(allergyType.getName())))
                .collect(Collectors.toList());
        recipeAllergyRepository.saveAll(recipeAllergies); // 새로운 알레르기 정보 저장

        Recipe updatedRecipe = recipeRepository.save(savedRecipe);

        //creator와 recipe연결
        MemberRecipe memberRecipe = MemberRecipe.builder()
                .recipe(recipe)
                .member(memberRepository.findById(creatorId).orElseThrow()).build();//
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
                .recipeImgs(Arrays.asList(updatedRecipe.getRecipeImgs().split(",")))
                .name(updatedRecipe.getRecipeName())
                .description(updatedRecipe.getRecipeDescription())
                .difficulty(updatedRecipe.getDifficulty())
                .cookingTime(updatedRecipe.getCookingTime())
                .categoryName(updatedRecipe.getCategory().getCategoryName())
                .tags(tags.stream().map(Tag::getTagName).collect(Collectors.toList()))
                .ingredients(ingredientFormatter.parseIngredientFormList(updatedRecipe.getIngredients()))
                .recipeContents(Arrays.asList(updatedRecipe.getRecipeContents().split("/")))
                .recipeDetailImgs(Arrays.asList(updatedRecipe.getRecipeDetailImgs().split(",")))
                .timers(Arrays.stream(updatedRecipe.getTimers().split(",")).map(Integer::parseInt).collect(Collectors.toList()))
                .allergyList(recipeAllergies.stream().map(ra -> ra.getAllergy().getAllergyName()).collect(Collectors.toList()))
                .build();
    }

}
