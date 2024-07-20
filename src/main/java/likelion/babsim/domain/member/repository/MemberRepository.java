package likelion.babsim.domain.member.repository;

import likelion.babsim.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
