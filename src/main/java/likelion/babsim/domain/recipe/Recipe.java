package likelion.babsim.domain.recipe;

import jakarta.persistence.*;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.category.Category;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.tag.Tag;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="recipe_id")
    private Long id;
    private String creatorId;
    private String recipeImgs;
    private String recipeName;
    private String recipeDescription;
    @Column(length = 60000)
    private String nutritionInfo;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private Integer cookingTime;
    @Column(length = 60000)
    private String recipeDetailImgs;
    @Column(length = 60000)
    private String ingredients;
    @Column(length = 60000)
    private String recipeContents;
    private String timers;
    private Long forkedRecipeId;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<MemberRecipe> memberRecipes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Tag> tags;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<RecipeReview> reviews;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Likes> likes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<RecipeAllergy> recipeAllergies;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Nft nft;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private CookedRecord cookedRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @Builder
    public Recipe(Long id, String creatorId, String recipeImgs, String recipeName, String recipeDescription, String nutritionInfo, Difficulty difficulty, Integer cookingTime, String recipeDetailImgs, String ingredients, String recipeContents, String timers,Long forkedRecipeId, List<MemberRecipe> memberRecipes, List<Tag> tags,Category category, List<RecipeReview> reviews, List<Likes> likes, List<RecipeAllergy> recipeAllergies, Nft nft, CookedRecord cookedRecord) {
        this.id = id;
        this.creatorId = creatorId;
        this.recipeImgs = recipeImgs;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.nutritionInfo = nutritionInfo;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
        this.recipeDetailImgs = recipeDetailImgs;
        this.ingredients = ingredients;
        this.recipeContents = recipeContents;
        this.timers = timers;
        this.forkedRecipeId = forkedRecipeId;
        this.memberRecipes = memberRecipes;
        this.tags = tags;
        this.category = category;
        this.reviews = reviews;
        this.likes = likes;
        this.recipeAllergies = recipeAllergies;
        this.nft = nft;
        this.cookedRecord = cookedRecord;
    }

    public void updateRecipeInfo(String recipeImgs, String recipeName, String recipeDescription, String nutritionInfo, Difficulty difficulty, Integer cookingTime, String recipeDetailImgs, String ingredients, String recipeContents, String timers, Category category) {
        this.recipeImgs = recipeImgs;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.nutritionInfo = nutritionInfo;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
        this.recipeDetailImgs = recipeDetailImgs;
        this.ingredients = ingredients;
        this.recipeContents = recipeContents;
        this.timers = timers;
        this.category = category;
        this.tags.clear();
        this.recipeAllergies.clear();
    }

    public void updateTags(List<Tag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }

    public void updateAllergies(List<RecipeAllergy> recipeAllergies) {
        this.recipeAllergies.clear();
        this.recipeAllergies.addAll(recipeAllergies);
    }
}
