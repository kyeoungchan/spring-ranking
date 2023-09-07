package springschool.ranking;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor // 생성자
@NoArgsConstructor // 임베디드 타입도 기본 생성자가 필요하다.
public class Semester {
    @Column(name = "semester_year")
    private Integer year;
    private Integer semester;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester instanceSemester = (Semester) o;
        return Objects.equals(year, instanceSemester.year) && Objects.equals(semester, instanceSemester.semester);
    }

    @Override
    public int hashCode() { // hashCode도 구현을 같이 해줘야 자바 컬렉션에서 효율적으로 사용할 수 있다.
        return Objects.hash(year, semester);
    }
}
