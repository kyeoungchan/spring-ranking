package springschool.ranking.student.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.exception.repository.UnValidatedException;
import springschool.ranking.student.domain.*;
import springschool.ranking.student.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;
    private final MessageSource messageSource;

    @PostMapping("/register/v1")
    StudentDto registerV1(@Validated @RequestBody StudentAddDto addDto, BindingResult bindingResult) {
        log.info("학생 등록 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = generateErrorMessages(bindingResult);
            throw new UnValidatedException("학생 등록 검증에 실패하였습니다." + message);
        }

        log.info("학생등록 성공 로직 실행");
        Student registerStudent = new Student(addDto.getName(), addDto.getScore(), Grade.valueOf(addDto.getGrade()), addDto.getRate());
        studentService.register(registerStudent);
        return new StudentDto(registerStudent.getId(), registerStudent.getName());
    }

    @PostMapping("/{studentId}/edit/v1")
    StudentDto updateV1(@PathVariable Long studentId, @Validated @RequestBody StudentUpdateDto updateDto, BindingResult bindingResult) {
        log.info("학생수정 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = generateErrorMessages(bindingResult);
            throw new UnValidatedException("학생수정 검증에 실패하였습니다." + message);
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

    private String generateErrorMessages(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String message = "";
        for (ObjectError error : allErrors) {
            // errors.properties에서 메시지를 가져오는데, 만약 값이 없으면 디폴트 메시지를 보낸다.
            message += " " + messageSource.getMessage(error.getCodes()[1], error.getArguments(), error.getDefaultMessage(), Locale.KOREA);
        }
        return message;
    }
}
