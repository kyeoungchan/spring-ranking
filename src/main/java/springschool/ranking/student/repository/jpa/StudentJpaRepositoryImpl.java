package springschool.ranking.student.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;
import springschool.ranking.student.repository.StudentSearch;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentJpaRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository studentJpaRepository;

    @Override
    public void save(Student student) {
        studentJpaRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long studentId) {
        return studentJpaRepository.findById(studentId);
    }

    @Override
    public List<Student> findAll() {
        return studentJpaRepository.findAll();
    }

    /**
     * MyBatis 적용 예정
     * 다음에 개발
     */
    @Override
    public List<Student> findAllByDto(StudentSearch studentSearch) {
        return null;
    }
}
