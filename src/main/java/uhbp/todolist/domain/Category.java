package uhbp.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    STUDY(1), EXERCISE(2), APPOINTMENT(3), OTHER(4);
    private int index;

    // index를 기반으로 Enum을 생성하는 정적 메서드
    public static Category fromIndex(int index) {
        for (Category enumValue : values()) {
            if (enumValue.getIndex() == index) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("해당 인덱스를 가진 Enum 상수가 없습니다.");
    }
}
