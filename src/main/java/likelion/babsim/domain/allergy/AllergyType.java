package likelion.babsim.domain.allergy;

public enum AllergyType {
    EGG(1, "알류"),
    MILK(2, "우유"),
    BUCKWHEAT(3, "메밀"),
    PEANUT(4, "땅콩"),
    SOYBEAN(5, "대두"),
    WHEAT(6, "밀"),
    PINE_NUT(7, "잣"),
    WALNUT(8, "호두"),
    CRAB(9, "게"),
    SHRIMP(10, "새우"),
    SQUID(11, "오징어"),
    MACKEREL(12, "고등"),
    SHELLFISH(13, "조개류"),
    PEACH(14, "복숭아"),
    TOMATO(15, "토마토"),
    CHICKEN(16, "닭고기"),
    PORK(17, "돼지고기"),
    BEEF(18, "쇠고기"),
    SULPHURIC_ACID(19, "아황산류");

    private final int id;
    private final String name;

    AllergyType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Static method to get an AllergyType by id
    public static AllergyType getById(int id) {
        for (AllergyType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
