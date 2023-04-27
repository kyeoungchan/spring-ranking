package springschool.ranking.student;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryStudentRepository implements StudentRepository{
    private static Map<Long, Student> store = new HashMap<>();


    @Override
    public void save(Student student) {
        store.put(student.getId(), student);
    }

    @Override
    public Student findById(Long studentId) {
        return store.get(studentId);
    }
}
