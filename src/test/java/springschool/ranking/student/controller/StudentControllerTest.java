package springschool.ranking.student.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.student.controller.dto.StudentCheckDto;
import springschool.ranking.student.service.StudentAddDto;
import springschool.ranking.student.service.StudentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StudentControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired StudentController studentController;
    @Autowired StudentService studentService;
    @Autowired MemberRepository memberRepository;

    private static final String BASE_URL = "/api/student";

    @Test
    @DisplayName("저장 테스트")
    void registerV1() throws Exception {

        // given

        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        StudentAddDto dto = new StudentAddDto("s1", "010", new Semester(1, 1), teacher.getId(), teacher.getName());

        StudentCheckDto resultDto = new StudentCheckDto(dto.getName(), dto.getPhoneNumber(), dto.getTeacherName());

        // when
        /* Object를 JSON으로 변환 */
        String body = mapper.writeValueAsString(dto);
        String responseBody = mapper.writeValueAsString(resultDto);

        // then
        mvc.perform(post(BASE_URL + "/register/v1")
                        .content(body) // HTTP Body에 데이터를 담는다.
                        .contentType(MediaType.APPLICATION_JSON) // 보내는 데이터와 타입을 명시
                )
                .andExpect(status().isOk());
    }

/*
    // perform() - 요청을 전송하는 역할, 결과로 ResultActions객체를 받고, 이 객체에서 리턴값을 검증하고 확인할 수 있는 andExcpect()메소드를 제공함.
        mvc.perform(get("/"))
            //상태코드 -> isOk()는 상태코드 200
            .andExpect(status().isOk())
            // 응답본문의 내용을 검증함 -> Controller에서 'home'을 리턴하기 때문에 이값이 맞는지 검증한다.
            .andExpect(content().string(home));
*/

    @Test
    void checkV1() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");

        StudentAddDto dto = new StudentAddDto("s1", "010", new Semester(1, 1), teacher.getId(), teacher.getName());


        // when

        // then

    }

    @Test
    void studentListV1() {
        // given

        // when

        // then

    }

    @Test
    void studentListWithNameV1() {
        // given

        // when

        // then

    }

    @Test
    void updateV1() {
        // given

        // when

        // then

    }

    @Test
    void deleteV1() {
        // given

        // when

        // then

    }
}