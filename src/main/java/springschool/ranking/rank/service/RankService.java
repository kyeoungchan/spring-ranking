package springschool.ranking.rank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final StudentRepository studentRepository;
    private final RankPolicyService rankPolicy;

    /**
     * @param id 해당 학생의 id가 주어진다.
     * @return Rank 객체를 반환해준다.
     */
    public Rank createRank(Long id) {

        // 매번 rank 결과를 반환할 때마다 전체 석차를 출력시켜준다.
        rankPolicy.printRankList();

        Student student = studentRepository.findById(id);
        Rank rank = new Rank(student.getId(), student.getName(), rankPolicy.getPolicy(), rankPolicy.rank(student));

        return rank;
    }

    /**
     * 정책에 맞춰 학생들을 정렬시킨 후 Rank 객체에 매핑시켜서 리스트를 반환한다.
     * @return
     */
    public List<Rank> getRankList() {
        List<Student> sortedStudents = rankPolicy.getSortedList();
        ArrayList<Rank> rankList = new ArrayList<>();
        for (Student student : sortedStudents) {
            rankList.add(createRank(student.getId()));
        }
        return rankList;
    }
}
