package springschool.ranking.student.repository;

import org.springframework.stereotype.Repository;
import springschool.ranking.student.domain.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryStudentRepository implements StudentRepository{
    private static Map<Long, Student> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public void save(Student student) {
        student.setId(++sequence);
        store.put(student.getId(), student);
    }

    @Override
    public Student findById(Long studentId) {
        return store.get(studentId);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
