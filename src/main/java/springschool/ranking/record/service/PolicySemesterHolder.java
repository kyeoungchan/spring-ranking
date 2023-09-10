package springschool.ranking.record.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springschool.ranking.Semester;

import javax.annotation.PreDestroy;

@Component
@Slf4j
public class PolicySemesterHolder {

    public ThreadLocal<String> policyHolder = new ThreadLocal<>();
    public ThreadLocal<Semester> semesterHolder = new ThreadLocal<>();

    @PreDestroy
    private void flushPolicyHolder() {
        log.info("***Destroying PolicyHolder and SemesterHolder***");
        policyHolder.remove();
        semesterHolder.remove();
    }

}
