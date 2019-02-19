package kr.ac.skuniv.realestate;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by youngman on 2019-01-22.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByName(String name);

    //메소드 이름 분석(조건절)
    Member findByNameAndAge(String name, int age);

    Member findByNameOrAge(String name, int age);
}
