package springschool.ranking.student.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.Semester;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.student.domain.Student;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentRepository /*implements StudentRepository*/ {

    private final StudentJpaRepository studentJpaRepository;

    public void save(Student student) {
        studentJpaRepository.save(student);
    }

    public Student findById(Long studentId) {
        return studentJpaRepository.findById(studentId).orElseThrow(NoSuchIdInDbException::new);
    }

    public List<Student> findAll() {
        return studentJpaRepository.findAll();
    }

    public List<Student> findAllBySemester(Semester semester) {
        return studentJpaRepository.findAllBySemester(semester.getYear(), semester.getSemester());
    }


    /**
     * QueryDsl 적용 예정
     * 다음에 개발
     */
    public List<Student> findAllByDto(StudentSearch studentSearch) {
        return null;
    }
}
