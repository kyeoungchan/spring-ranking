package springschool.ranking.student.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.exception.UnValidatedException;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.domain.StudentAddDto;
import springschool.ranking.student.domain.StudentDto;
import springschool.ranking.student.domain.StudentUpdateDto;
import springschool.ranking.student.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register/v1")
    StudentDto registerV1(@Validated @RequestBody StudentAddDto addDto, BindingResult bindingResult) {
        log.info("학생 등록 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            throw new UnValidatedException("학생 등록 검증에 실패하였습니다.");
        }

        log.info("학생등록 성공 로직 실행");
        Student registerStudent = new Student(addDto.getName(), addDto.getScore(), addDto.getGrade(), addDto.getRate());
        studentService.register(registerStudent);
        return new StudentDto(registerStudent.getId(), registerStudent.getName());
    }

    @PostMapping("/{studentId}/edit/v1")
    StudentDto updateV1(@PathVariable Long studentId, @Validated @RequestBody StudentUpdateDto updateDto, BindingResult bindingResult) {
        log.info("학생수정 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            throw new UnValidatedException("학생수정 검증에 실패하였습니다.");
        }

        log.info("학생수정 성공 로직 실행");
        Student updatedStudent = studentService.edit(studentId, updateDto);
        return new StudentDto(updatedStudent.getId(), updatedStudent.getName());
    }

    @GetMapping("/students/v1")
    List<StudentDto> studentListV1() {
        List<Student> studentList = studentService.findStudentList();
        ArrayList<StudentDto> studentDtoList = new ArrayList<>();
        for (Student student : studentList) {
            studentDtoList.add(new StudentDto(student.getId(), student.getName()));
        }
        return studentDtoList;
    }
}
