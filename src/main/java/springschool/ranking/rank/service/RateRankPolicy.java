package springschool.ranking.rank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springschool.ranking.rank.Policy;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

//@Component
@Slf4j
@RequiredArgsConstructor
public class RateRankPolicy implements RankPolicy {

    private final StudentRepository studentRepository;
    private static List<Map.Entry<Long, Double>> list = new ArrayList<>();

    /**
     * 백분율이 큰 순으로 -> 내림차순
     */
    @Override
    public void sortRank() {

        list = studentRepository.findAll().stream()
                .collect(Collectors.toMap(Student::getId, Student::getRate))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public int rank(Student student) {

        sortRank();

        return list.stream().map(Map.Entry::getValue) // Entry = [id, rate]
                .collect(Collectors.toList())
                .indexOf(student.getRate()) + 1;
    }

    @Override
    public void printRankList() {

        sortRank();

        log.info("=== 백분율의 크기가 큰 순서로 정렬 ===");

        // id : name : rate 출력
        list.stream().map(e -> String.format("%d : %s : %.1f", e.getKey(),
                        studentRepository.findById(e.getKey()).getName(), e.getValue()))
                .forEach(System.out::println);
    }

    @Override
    public Policy getPolicy() {
        return Policy.RATE;
    }

}
