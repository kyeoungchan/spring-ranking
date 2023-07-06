package springschool.ranking.rank.service;

import org.springframework.stereotype.Service;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankServiceImpl implements RankService {

    private final StudentRepository studentRepository;
    private final RankPolicyService rankPolicy;

    public RankServiceImpl(StudentRepository studentRepository, RankPolicyService rankPolicy) {
        this.studentRepository = studentRepository;
        this.rankPolicy = rankPolicy;
    }

    @Override
    public Rank createRank(Long id) {

        // 매번 rank 결과를 반환할 때마다 전체 석차를 출력시켜준다.
        rankPolicy.printRankList();

        Student student = studentRepository.findById(id);
        Rank rank = new Rank(student.getId(), student.getName(), rankPolicy.getPolicy(), rankPolicy.rank(student));

        return rank;
    }

    @Override
    public List<Rank> getRankList() {
        List<Student> sortedStudents = rankPolicy.getSortedList();
        ArrayList<Rank> rankList = new ArrayList<>();
        for (Student student : sortedStudents) {
            rankList.add(createRank(student.getId()));
        }
        return rankList;
    }
}
