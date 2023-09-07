package uhbp.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoCategory {
    STUDY(1), EXERCISE(2), APPOINTMENT(3), OTHER(4);
    private int categoryIndex;

    public int getIndex() {
        return categoryIndex;
    }

    // index를 기반으로 Enum을 생성하는 정적 메서드
    public static TodoCategory fromCategoryIndex(int categoryIndex) {
        for (TodoCategory enumValue : values()) {
            if (enumValue.getCategoryIndex() == categoryIndex) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("해당 인덱스를 가진 Enum 상수가 없습니다.");
    }

}
