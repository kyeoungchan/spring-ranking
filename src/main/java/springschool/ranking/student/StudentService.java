package springschool.ranking.student;

public interface StudentService {
    void register(Student student);

    Student findStudent(Long studentId);
}
