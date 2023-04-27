package springschool.ranking.student;

public interface StudentRepository {
    void save(Student student);

    Student findById(Long studentId);
}
