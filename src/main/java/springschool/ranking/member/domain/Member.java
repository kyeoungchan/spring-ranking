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
}
