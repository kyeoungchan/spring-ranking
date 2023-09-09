package springschool.ranking.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import springschool.ranking.Semester;
import springschool.ranking.record.service.PolicySemesterHolder;
import springschool.ranking.record.service.policy.RecordPolicyConst;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ThreadLocalAspect {

    private final PolicySemesterHolder holder;

    @Around("execution(* springschool.ranking.record.service.RecordService.*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        if (holder.policyHolder.get() == null){
            holder.policyHolder.set(RecordPolicyConst.rankPolicy);
            log.info("AOP 적용. RankPolicy");
        }
        if (holder.semesterHolder.get() == null) {
            holder.semesterHolder.set(new Semester(1, 1));
            log.info("AOP 적용. RankPolicy");
        }

        Object result = joinPoint.proceed();

//        holder.policyHolder.remove();
//        holder.semesterHolder.remove();

        return result;
    }

    @Around("execution(* springschool.ranking.member.controller.MemberController.logout*(..))")
    public Object logoutExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        if ( holder.policyHolder.get() != null)
            holder.policyHolder.remove();
        if (holder.semesterHolder.get()!=null)
            holder.semesterHolder.remove();
        return result;
    }
}
