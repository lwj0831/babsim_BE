package likelion.babsim.domain.recipe;

public enum Difficulty {
    EASY("초급"),
    NORMAL("중급"),
    HARD("고급");

    private final String label;

    Difficulty(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
