package springschool.ranking;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor // 생성자
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 임베디드 타입도 기본 생성자가 필요하다.
public class Semester {
    private int year;
    private int semester;
}
