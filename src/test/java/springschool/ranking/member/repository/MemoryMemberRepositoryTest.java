package springschool.ranking.member.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoryMemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @AfterEach
    void afterEach() {
    }

    @Test
    void save() {

    }

    @Test
    void findById() {
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findAll() {
    }
}