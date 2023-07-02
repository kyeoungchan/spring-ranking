package springschool.ranking.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import springschool.ranking.rank.Policy;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.*;

//@Component
public class ScoreRankPolicy implements RankPolicy {

    private final StudentRepository studentRepository;
    private static Map<Long, Integer> store = new HashMap<>();
    private static List<Map.Entry<Long, Integer>> list = new ArrayList<>();


    @Autowired
    public ScoreRankPolicy(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * 언제 리포지토리로부터 갱신된 데이터를 받을지 모르므로 매 메서드를 호출할 때마다 setList() 메서드도 호출한다.
     */
    public void setList() {
        // Repository로부터 받은 리스트를 store에 담기
        List<Student> tmp = this.studentRepository.findAll();
        for (Student student : tmp) {
            store.put(student.getId(), student.getScore());
        }


        // store를 ArrayList로 변환한 다음에 Collections.sort() 정렬
        Set<Map.Entry<Long, Integer>> set = store.entrySet();
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
        for (Map.Entry<Long, Integer> entry : list) {
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

        Iterator<Map.Entry<Long, Integer>> it = list.iterator();

        System.out.println("= 점수의 크기가 큰 순서로 정렬 =");
        while (it.hasNext()) {
            Map.Entry<Long, Integer> entry = it.next();
            String name = studentRepository.findById(entry.getKey()).getName();
            int score = entry.getValue().intValue();
            System.out.println(entry.getKey() + " : " + name + " : " + score);
        }
        System.out.println();
    }

    @Override
    public Policy getPolicy() {
        return Policy.SCORE;
    }

    /**
     * Map 자료형을 점수가 큰 사람부터 내림차순을 하기 위한 Comparator 구현
     */
    static class ScoreComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Map.Entry<?, ?> && o2 instanceof Map.Entry<?, ?>) {
                Map.Entry e1 = (Map.Entry) o1;
                Map.Entry e2 = (Map.Entry) o2;

                int v1 = ((Integer) e1.getValue()).intValue();
                int v2 = ((Integer) e2.getValue()).intValue();

                return v2 - v1;
            }
            return -1;
        }
    }

}
