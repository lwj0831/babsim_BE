package likelion.babsim.domain.tag.service;

import likelion.babsim.domain.tag.Tag;
import likelion.babsim.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<String> findTagNamesByRecipeId(Long recipeId){
        return tagRepository.findAllByRecipeId(recipeId).stream()
                .map(Tag::getTagName)
                .toList();
    }
    public Tag saveTag(Tag tag){
        return tagRepository.save(tag);
    }
}
