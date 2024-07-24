package likelion.babsim.domain.category;

import jakarta.persistence.*;
import likelion.babsim.domain.recipe.Recipe;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String categoryName;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Recipe> recipe;

    @Builder
    public Category(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
