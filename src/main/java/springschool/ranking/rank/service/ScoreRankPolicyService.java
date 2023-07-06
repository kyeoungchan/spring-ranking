package springschool.ranking.rank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springschool.ranking.rank.Policy;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

//@Component
@Slf4j
@RequiredArgsConstructor
public class ScoreRankPolicyService implements RankPolicyService {

    private final StudentRepository studentRepository;
    private static List<Map.Entry<Long, Integer>> list = new ArrayList<>();


    /**
     * 점수의 크기가 큰 순으로 -> 내림차순
     */
    @Override
    public void sortRank() {

        list = studentRepository.findAll().stream()
                .collect(Collectors.toMap(Student::getId, Student::getScore))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

    }

    @Override
    public int rank(Student student) {

        sortRank();

        return list.stream().map(Map.Entry::getValue) // Entry = [id, score]
                .collect(Collectors.toList())
                .indexOf(student.getScore()) + 1;
    }

    @Override
    public void printRankList() {

        sortRank();

        log.info("=== 점수의 크기가 큰 순서로 정렬 ===");

        // id : name : score 출력
        list.stream().map(e -> String.format("%d : %s : %d", e.getKey(),
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
        return Policy.SCORE;
    }

}
