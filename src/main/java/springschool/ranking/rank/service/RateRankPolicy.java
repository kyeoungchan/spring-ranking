package springschool.ranking.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springschool.ranking.rank.Policy;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.*;

@Component
public class RateRankPolicy implements RankPolicy {

    private final StudentRepository studentRepository;
    private static Map<Long, Double> store = new HashMap<>();
    private static List<Map.Entry<Long, Double>> list = new ArrayList<>();


    @Autowired
    public RateRankPolicy(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * 언제 리포지토리로부터 갱신된 데이터를 받을지 모르므로 매 메서드를 호출할 때마다 setList() 메서드도 호출한다.
     */
    public void setList() {
        // Repository로부터 받은 리스트를 store에 담기
        List<Student> tmp = this.studentRepository.findAll();
        for (Student student : tmp) {
            store.put(student.getId(), student.getRate());
        }


        // store를 ArrayList로 변환한 다음에 Collections.sort() 정렬
        Set<Map.Entry<Long, Double>> set = store.entrySet();
        list = new ArrayList<>(set); // ArrayList(Collection c)

        // list를 생성하자마자 정렬시켜준다.
        sortRank();
    }

    @Override
    public void sortRank() {

        // static void sort(List list, Comparator c)
        Collections.sort(list, new ScoreComparator());
    }

    @Override
    public int rank(Student student) {

        setList();

        int i = 0;
        for (Map.Entry<Long, Double> entry : list) {
            if (entry.getKey() == student.getId()) {
                break;
            }
            i++;
        }

        return i + 1;
    }

    @Override
    public void printRankList() {
        setList();

        Iterator<Map.Entry<Long, Double>> it = list.iterator();

        System.out.println("= 백분율의 크기가 큰 순서로 정렬 =");
        while (it.hasNext()) {
            Map.Entry<Long, Double> entry = it.next();
            String name = studentRepository.findById(entry.getKey()).getName();
            double rate = entry.getValue();
            System.out.println(entry.getKey() + " : " + name + " : " + rate);
        }
        System.out.println();
    }

    @Override
    public Policy getPolicy() {
        return Policy.RATE;
    }

    /**
     * Map 자료형을 백분율이 큰 사람부터 내림차순을 하기 위한 Comparator 구현
     */
    static class ScoreComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Map.Entry<?, ?> && o2 instanceof Map.Entry<?, ?>) {
                Map.Entry e1 = (Map.Entry) o1;
                Map.Entry e2 = (Map.Entry) o2;

                double v1 = ((Double) e1.getValue());
                double v2 = ((Double) e2.getValue());

                // java.util의 Comparator의 compare()를 오버라이딩하므로 반환형이 int형이어야한다. 그래서 삼항연산자로 반환값을 대체하였다.
                return (v2 - v1 > 0) ? 1 : (v2 - v1 < 0) ? -1 : 0;
            }
            return -1;
        }
    }

}
