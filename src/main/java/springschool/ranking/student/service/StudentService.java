package springschool.ranking.student.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final MemberRepository memberRepository;

    /**
     * 학생을 등록하는 로직
     * 교사에 대한 정보를 전달 받으면 교사까지 같이 매칭시켜주며 학생을 등록한다.
     */
    @Transactional
    public Student register(StudentAddDto addDto) {

        String name = addDto.getName();
        String phoneNumber = addDto.getPhoneNumber();
        Semester semester = addDto.getSemester();

        Member teacher = memberRepository.findById(addDto.getTeacherId());

        return studentRepository.save(Student.createStudent(name, phoneNumber, semester, teacher));
    }

    /**
     * 학생 한 명 조회
     */
    public Student findStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }


    /**
     * 전체 학생 리스트 출력
     */
    public List<Student> findStudents() {
        return studentRepository.findAll();
    }

    /**
     * 이름으로 검색 결과 학생 리스트 반환
     */
    public List<Student> findStudentsByName(String name) {
        return studentRepository.findAllByName(name);
    }

    /**
     * 학생 수정 로직
     * @param studentId 수정 대상
     * @param updateDto
     *  - studentName
     *  - phoneNumber
     *  - teacherId
     *  - teacherName
     */
    @Transactional
    public void edit(Long studentId, StudentUpdateDto updateDto) {

        Student updatedStudent = studentRepository.findById(studentId);
        Member teacher = memberRepository.findById(updateDto.getTeacherId());

        updatedStudent.updateStudent(updateDto.getName(), updateDto.getPhoneNumber(), teacher);
    }

    @Transactional
    public void delete(Long studentId) {
        studentRepository.deleteOne(studentId);
    }
}
