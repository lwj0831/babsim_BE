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
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private Integer cookingTime;
    @Column(length = 65535)
    private String recipeDetailImgs;
    private String ingredients;
    @Column(length = 65535)
    private String recipeContents;
    private String timers;
    private String ownerId;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MemberRecipe> memberRecipes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Tag> tags;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RecipeReview> reviews;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Likes> likes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RecipeAllergy> recipeAllergies;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Nft nft;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private CookedRecord cookedRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @Builder
    public Recipe(Long id, String creatorId, String recipeImgs, String recipeName, String recipeDescription, Difficulty difficulty, Integer cookingTime, String recipeDetailImgs, String ingredients, String recipeContents, String timers, String ownerId, List<MemberRecipe> memberRecipes, List<Tag> tags,Category category, List<RecipeReview> reviews, List<Likes> likes, List<RecipeAllergy> recipeAllergies, Nft nft, CookedRecord cookedRecord) {
        this.id = id;
        this.creatorId = creatorId;
        this.recipeImgs = recipeImgs;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
        this.recipeDetailImgs = recipeDetailImgs;
        this.ingredients = ingredients;
        this.recipeContents = recipeContents;
        this.timers = timers;
        this.ownerId = ownerId;
        this.memberRecipes = memberRecipes;
        this.tags = tags;
        this.category = category;
        this.reviews = reviews;
        this.likes = likes;
        this.recipeAllergies = recipeAllergies;
        this.nft = nft;
        this.cookedRecord = cookedRecord;
    }
}
