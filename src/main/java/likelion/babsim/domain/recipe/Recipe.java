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
    private Difficulty difficulty;
    private Integer cookingTime;
    private String recipeDetailImgs;
    private String ingredients;
    private String recipeContents;
    private String timers;
    private Long cookedCount;
    private String ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

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

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL)
    @ToString.Exclude
    private CookedRecord cookedRecord;

    @Builder
    public Recipe(Long id, String recipeName, String recipeImgs, int cookingTime,Member member,String creatorId,String ownerId,List<Category> categories) {
        this.id = id;
        this.recipeName = recipeName;
        this.recipeImgs = recipeImgs;
        this.cookingTime = cookingTime;
        this.member = member;
        this.creatorId = creatorId;
        this.ownerId = ownerId;
        this.categories = categories;
    }
}
