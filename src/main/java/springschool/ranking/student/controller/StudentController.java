package springschool.ranking.student.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.exception.repository.UnValidatedException;
import springschool.ranking.student.controller.dto.StudentCheckDto;
import springschool.ranking.commondto.StudentCheckElementDto;
import springschool.ranking.commondto.StudentCheckListDto;
import springschool.ranking.student.domain.*;
import springschool.ranking.student.service.StudentAddDto;
import springschool.ranking.student.service.StudentService;
import springschool.ranking.student.service.StudentUpdateDto;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;
    private final MessageSource messageSource;

    /**
     * 학생 등록
     */
    @PostMapping("/register/v1")
    public StudentCheckDto registerV1(@Validated @RequestBody StudentAddDto addDto, BindingResult bindingResult) {
        log.info("학생 등록 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = generateErrorMessages(bindingResult);
            throw new UnValidatedException("학생 등록 검증에 실패하였습니다." + message);
        }

        log.info("학생등록 성공 로직 실행");

        Student registeredStudent = studentService.register(addDto);
        return checkV1(registeredStudent.getId());
    }

    /**
     * 해당 학생 조회 로직
     */
    @GetMapping("/{studentId}/check/v1")
    public StudentCheckDto checkV1(@PathVariable Long studentId) {
        Student student = studentService.findStudent(studentId);
        return new StudentCheckDto(student.getName(), student.getPhoneNumber(),
                student.getTeacher().getName());
    }

    /**
     * 학생들 목록 조회 로직
     */
    @GetMapping("/students/v1")
    public StudentCheckListDto studentListV1() {
        List<Student> studentList = studentService.findStudents();

        return generateStudentCheckListDto(studentList);
    }

    /**
     * 이름 검색 결과 학생들 목록 조회
     */
    @GetMapping("/students/{name}/v1")
    public StudentCheckListDto studentListWithNameV1(@PathVariable("name") String name) {
        List<Student> studentList = studentService.findStudentsByName(name);

        return generateStudentCheckListDto(studentList);
    }

    /**
     * 해당 학생 수정 로직
     * @return 등록한 학생 조회 메서드로 리다이렉트
     */
    @PostMapping("/{studentId}/edit/v1")
    public StudentCheckDto updateV1(@PathVariable Long studentId, @Validated @RequestBody StudentUpdateDto updateDto, BindingResult bindingResult) {
        log.info("학생수정 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = generateErrorMessages(bindingResult);
            throw new UnValidatedException("학생수정 검증에 실패하였습니다." + message);
        }

        log.info("학생수정 성공 로직 실행");
        studentService.edit(studentId, updateDto);

        return checkV1(studentId);
    }

    /**
     * 해당 학생 삭제 로직
     * @return 학생을 삭제한 후 학생들 목록 페이지로 리다이렉트한다.
     */
    @PostMapping("/{studentId}/delete/v1")
    public StudentCheckListDto deleteV1(@PathVariable("studentId") Long studentId) {
        studentService.delete(studentId);
        return studentListV1();
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

    private static StudentCheckListDto generateStudentCheckListDto(List<Student> studentList) {
        StudentCheckListDto result = new StudentCheckListDto();

        for (Student student : studentList) {
            result.getStudents().add(new StudentCheckElementDto(student.getId(), student.getName()));
        }
        result.setCount(studentList.size());

        return result;
    }
}
