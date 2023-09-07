package springschool.ranking.student.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.student.domain.Student;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class StudentRepository {

    private final StudentJpaRepository studentJpaRepository;

    public Student save(Student student) {
        return studentJpaRepository.save(student);
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

    public List<Student> findAllByName(String name) {
        return studentJpaRepository.findAllByName(name);
    }

    public void deleteOne(Long studentId) {
        studentJpaRepository.delete(findById(studentId));
    }

    /**
     * QueryDsl 적용 예정
     * 다음에 개발
     */
    public List<Student> findAllByDto(StudentSearch studentSearch) {
        return null;
    }
}
