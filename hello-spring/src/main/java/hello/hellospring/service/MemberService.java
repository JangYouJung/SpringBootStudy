package hello.hellospring.service;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;
public class MemberService {
    private final MemberRepository memberRepository = new
            MemoryMemberRepository();
    /**
     * 회원가입
     */

    /********* [1] 중복 회원 검색 초기 코드: Optional 사용해서 null 자동 확인
    public Long join(Member member){
        //같은 이름이 있는 중복 회원 검색
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(member1 -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        memberRepository.save(member);
        return member.getId();
    }
    */

     /****** [2] 증복 회원 검색 두 번째 코드: Optional 지우고 바로 .present 사용
    public Long join(Member member){
        //같은 이름이 있는 중복 회원 검색
        memberRepository.findByName(member.getName())
                .ifPresent(member1 -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        memberRepository.save(member);
        return member.getId();
    }
    */

     // [3] 중복 회원 검색 세 번째 코드: findByName을 Extract Method로 따로 빼줌
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}