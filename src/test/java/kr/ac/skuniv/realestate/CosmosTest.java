package kr.ac.skuniv.realestate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by youngman on 2019-01-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CosmosTest {
    @Autowired
    private MemberRepository memberRepository;
    private Logger logger = LoggerFactory.getLogger(QueryTest.class);

    /*@Test
    public void save() {
        Member saveMember = new Member();
        saveMember.setName("이영준");
        saveMember.setAge(26);
        memberRepository.save(saveMember);
    }

    @Test
    public void delete() {
        Member deleteMember = memberRepository.findMemberByName("이영준");
        logger.info(deleteMember.toString());
        memberRepository.delete(deleteMember);
    }

    @Test
    public void find() {
        Member saveMember = new Member();
        saveMember.setName("박춘소");
        saveMember.setAge(25);
        memberRepository.save(saveMember);

        Member findMember = memberRepository.findMemberByName("박춘소");
        logger.info(findMember.toString());
    }

    @Test
    public void update() {
        Member updateMember = memberRepository.findMemberByName("박춘소");
        updateMember.setAge(27);
        memberRepository.save(updateMember);

        Member findMember = memberRepository.findMemberByName("박춘소");
        logger.info(findMember.toString());
    }

    @Test
    public void and() {
        Member andMember = memberRepository.findByNameAndAge("박춘소", 27);
        logger.info(andMember.toString());
    }*/
}
