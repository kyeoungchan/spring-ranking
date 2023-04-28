package springschool.ranking.rank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springschool.ranking.student.Grade;
import springschool.ranking.student.Student;
import springschool.ranking.student.StudentRepository;

import java.util.*;

//@Component
public class GradeRankPolicy implements RankPolicy{
    private final StudentRepository studentRepository;
    private static Map<Long, Grade> store = new HashMap<>();
    private static List<Map.Entry<Long, Grade>> list = new ArrayList<>();


    @Autowired
    public GradeRankPolicy(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * 언제 리포지토리로부터 갱신된 데이터를 받을지 모르므로 매 메서드를 호출할 때마다 setList() 메서드도 호출한다.
     */
    public void setList() {
        // Repository로부터 받은 리스트를 store에 담기
        List<Student> tmp = this.studentRepository.findAll();
        for (Student student : tmp) {
            store.put(student.getId(), student.getGrade());
        }


        // store를 ArrayList로 변환한 다음에 Collections.sort() 정렬
        Set<Map.Entry<Long, Grade>> set = store.entrySet();
        list = new ArrayList<>(set); // ArrayList(Collection c)

        // list를 생성하자마자 정렬시켜준다.
        sortRank();
    }

    @Override
    public void sortRank() {

        // static void sort(List list, Comparator c)
        Collections.sort(list, new GradeRankPolicy.GradeComparator());
    }

    @Override
    public int rank(Student student) {

        setList();

        int i=0;
        for (Map.Entry<Long, Grade> entry : list) {
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

        Iterator<Map.Entry<Long, Grade>> it = list.iterator();

        System.out.println("= 등급의 크기가 작은 순서로 정렬 =");
        while (it.hasNext()) {
            Map.Entry<Long, Grade> entry = it.next();
            String name = studentRepository.findById(entry.getKey()).getName();
            Grade grade = entry.getValue();
            System.out.println(entry.getKey() + " : " + name + " : " + grade);
        }
        System.out.println();
    }

    @Override
    public Policy getPolicy() {
        return Policy.GRADE;
    }

    /**
     * Map 자료형을 등급이 작은 사람부터 오름차순을 하기 위한 Comparator 구현
     */
    static class GradeComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Map.Entry<?, ?> && o2 instanceof Map.Entry<?, ?>) {
                Map.Entry e1 = (Map.Entry) o1;
                Map.Entry e2 = (Map.Entry) o2;

                Grade g1 = (Grade) e1.getValue();
                Grade g2 = (Grade) e2.getValue();

                int v1 = g1.getValue();
                int v2 = g2.getValue();

                return v1 - v2;
            }
            return -1;
        }
    }

}
