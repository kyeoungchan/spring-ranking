package springschool.ranking.member.domain;

import lombok.Getter;
import springschool.ranking.student.domain.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String userId;
    private String password;

    @OneToMany(mappedBy = "teacher")
    private List<Student> students = new ArrayList<>();

    public static Member createMember(String userId, String password, String name) {
        Member member = new Member();
        member.userId = userId;
        member.password = password;
        member.name = name;
        return member;
    }

    /**
     * 회원 정보 수정
     * 수정할 수 있는 정보는 이름과 비밀번호
     */
    public void updateMember(String changingName, String changingPassword) {
        this.name = changingName;
        this.password = changingPassword;
    }
}
