package springschool.ranking.rank.service.policy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springschool.ranking.Semester;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.rank.Policy;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.dto.RankListDto;
import springschool.ranking.rank.repository.RankRepository;
import springschool.ranking.student.repository.StudentRepository;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class GradeRankPolicyService implements RankPolicyService {

    private final StudentRepository studentRepository;
    private final RankRepository rankRepository;

    private ThreadLocal<List<GradeSortingDto>> list = new ThreadLocal<>();

    @PreDestroy
    public void end() {
        // 애플리케이션 종료 전 remove() 호출
        list.remove();
    }

    /**
     * 등급이 작은 순(숫자 기준)으로 -> 오름차순
     */
    @Override
    public RankListDto sortRank(Semester semester) {

        // 해당 학기의 Rank 객체 리스트 추출
        List<Rank> ranks = rankRepository.findAllBySemesters(semester);

        // 추출한 리스트를 정렬 및 내부 DTO 로 변환
        list.set(ranks.stream().map(r -> new GradeSortingDto(r, r.getGrade()))
                .sorted(Comparator.comparing(GradeSortingDto::getGrade))
                .collect(Collectors.toList()));

        // 스트림 변환 중 인덱스 추출을 위한 AtomicInteger 사용
        AtomicInteger index = new AtomicInteger();

        List<RankListDto.RankElementDto> rankList = list.get().stream()
                .map(dto -> new RankListDto.RankElementDto(
                        dto.rank.getStudent().getId(),
                        dto.rank.getStudent().getName(),
                        dto.rank.getStudent().getTeacher().getName(),
                        index.getAndDecrement() + 1))
                .collect(Collectors.toList());

        RankListDto rankListDto = new RankListDto();
        rankListDto.setRankList(rankList);
        rankListDto.setPolicy(Policy.GRADE);
        rankListDto.setCount(rankList.size());
        return rankListDto;
    }

    @Override
    public int getRankOne(Long studentId, Semester semester) {

        if (list.get() == null) {
            // 만약 정렬한 적이 없다면 정렬 후 등수 조회
            sortRank(semester);
        }

        Rank findRank = rankRepository.findRankByStudentAndSemester(studentRepository.findById(studentId), semester);

        GradeSortingDto element = list.get().stream().filter(dto -> dto.rank.equals(findRank)).findFirst().orElseThrow(() -> new NoSuchIdInDbException("서비스에서 발생"));

        return list.get().indexOf(element) + 1; // 해당 DTO 의 인덱스 + 1 이 석차다.
    }

    @Data
    @AllArgsConstructor
    static class GradeSortingDto {
        private Rank rank;
        private int grade;
    }
/*
return list.stream().map(Map.Entry::getValue) // Entry = [id, grade]
                .collect(Collectors.toList())
            .indexOf(student.getGrade()) + 1;
*/

/*
    @Override
    public void printRankList() {

        sortRank();

        log.info("=== 등급의 크기가 작은 순서로 정렬 ===");

        // id : name : grade 출력
        list.stream().map(e -> String.format("%d : %s : %s", e.getKey(),
                        studentRepository.findById(e.getKey()).getName(), e.getValue()))
                .forEach(System.out::println);
    }

    @Override
    public List<Student> getSortedList() {
        sortRank();
        return list.stream().map(e -> studentRepository.findById(e.getKey())).collect(Collectors.toList());
    }

    @Override
    public Policy getPolicy() {
        return Policy.GRADE;
    }
*/

}
