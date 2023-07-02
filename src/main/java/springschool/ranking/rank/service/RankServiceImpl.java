package springschool.ranking.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

@Service
public class RankServiceImpl implements RankService {

    private final StudentRepository studentRepository;
    private final RankPolicy rankPolicy;

    @Autowired
    public RankServiceImpl(StudentRepository studentRepository, RankPolicy rankPolicy) {
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
}
