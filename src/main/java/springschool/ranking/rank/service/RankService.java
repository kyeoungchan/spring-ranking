package springschool.ranking.rank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.dto.RankInputDto;
import springschool.ranking.rank.repository.RankRepository;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService {

    private final StudentRepository studentRepository;
    private final RankRepository rankRepository;
    private final RankPolicyService rankPolicy;

    /**
     * 학생의 점수를 입력하는 로직
     */
    @Transactional // readOnly = false 로 변경
    public void inputRank(RankInputDto rankInputDto) {
        new Rank()
    }

}
