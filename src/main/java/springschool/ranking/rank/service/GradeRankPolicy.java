package springschool.ranking.rank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springschool.ranking.rank.Policy;
import springschool.ranking.student.domain.Grade;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class GradeRankPolicy implements RankPolicy{
    private final StudentRepository studentRepository;
    private static List<Map.Entry<Long, Grade>> list = new ArrayList<>();


    /**
     * 등급이 작은 순(숫자 기준)으로 -> 오름차순
     */
    @Override
    public void sortRank() {

        list = studentRepository.findAll().stream()
                .collect(Collectors.toMap(Student::getId, Student::getGrade))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
    }

    @Override
    public int rank(Student student) {

        sortRank();

        return list.stream().map(Map.Entry::getValue) // Entry = [id, grade]
                .collect(Collectors.toList())
                .indexOf(student.getGrade()) + 1;
    }

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
    public Policy getPolicy() {
        return Policy.GRADE;
    }

}
