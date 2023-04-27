package springschool.ranking.rank;

import springschool.ranking.student.Student;
import springschool.ranking.student.StudentRepository;

import java.util.*;

public class ScoreRankPolicy implements RankPolicy {

    private final StudentRepository studentRepository;
    private static Map<Long, Integer> store = new HashMap<>();
    private static List<Map.Entry<Long, Integer>> list;


    public ScoreRankPolicy(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void sortRank() {
        // store를 ArrayList로 변환한 다음에 Collections.sort() 정렬
        Set<Map.Entry<Long, Integer>> set = store.entrySet();
        List<Map.Entry<Long, Integer>> list = new ArrayList<>(set); // ArrayList(Collection c)

        // static void sort(List list, Comparator c)
        Collections.sort(list, new ScoreComparator());
    }

    @Override
    public int rank(Student student) {

        /*Iterator<Map.Entry<Long, Integer>> it = list.iterator();

        while (it.hasNext()) {
            Map.Entry<Long, Integer> entry = it.next();
            long id = entry.getKey();

        }*/

        int i=0;
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

        Iterator<Map.Entry<Long, Integer>> it = list.iterator();

        System.out.println("= 값의 크기가 큰 순서로 정렬 =");
        while (it.hasNext()) {
            Map.Entry<Long, Integer> entry = it.next();
            String name = studentRepository.findById(entry.getKey()).getName();
            int score = entry.getValue().intValue();
            System.out.println(entry.getKey() + " : " + name + " : " + score);
        }
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
