package likelion.babsim.domain.recipe;

import jakarta.persistence.*;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.category.Category;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="recipe_id")
    private Long id;
    private String creatorId;
    private String recipeImg;
    private String recipeName;
    private String recipeDescription;
    private Difficulty difficulty;
    private Integer cookingTime;
    private String ingredients;
    private String recipeContents;
    private String recipeDetailImg;
    private String recipeContent;
    private String timers;
    private boolean isForked;
    private Long cookedCount;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Tag> tags;

    @OneToMany(mappedBy = "recipe")
    @ToString.Exclude
    private List<Category> categories;

    @OneToMany(mappedBy = "recipe")
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


}
