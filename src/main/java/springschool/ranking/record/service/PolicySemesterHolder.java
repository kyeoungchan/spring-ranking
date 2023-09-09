package springschool.ranking.record.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springschool.ranking.Semester;
import springschool.ranking.record.service.policy.RecordPolicyConst;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
public class PolicySemesterHolder {

    public ThreadLocal<String> policyHolder = new ThreadLocal<>();
    public ThreadLocal<Semester> semesterHolder = new ThreadLocal<>();

/*
    @PostConstruct
    private void initHolders() {

        policyHolder.set(RecordPolicyConst.rankPolicy);
        log.info("Init Policy={}", policyHolder.get());

        semesterHolder.set(new Semester(1, 1));
        log.info("Init Semester={}", semesterHolder.get());
    }
*/

    @PreDestroy
    private void flushPolicyHolder() {
        log.info("***Destroying PolicyHolder and SemesterHolder***");
        policyHolder.remove();
        semesterHolder.remove();
    }

}
