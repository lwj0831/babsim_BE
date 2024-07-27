package likelion.babsim.domain.category.service;

import jakarta.annotation.PostConstruct;
import likelion.babsim.domain.category.Category;
import likelion.babsim.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        categoryRepository.save(new Category(1L,"Main Courses"));
        categoryRepository.save(new Category(2L,"Simple"));
        categoryRepository.save(new Category(3L,"Vegan"));
        categoryRepository.save(new Category(4L,"Snack"));
        categoryRepository.save(new Category(5L,"Baking"));
        categoryRepository.save(new Category(6L,"Diet"));
        categoryRepository.save(new Category(7L,"Oven"));
        categoryRepository.save(new Category(8L,"Keto"));
    }
}
