package hello.hellospring.service;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
class MemberServiceTest {

    /*------------전-------------
    MemberService memberService = new MemberService();
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    */
    //------------후--------------: MemberService에 생성자 만들어주고 berforeach->
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    //----------------
    @AfterEach
    public void afterEach() { //저번과
        memberRepository.clearStore();
    }
    @Test
    public void 회원가입() throws Exception { //테스트 코드는 외국과 교류하지 않는 이상 한국어로 작성해도 무방
        //Given
        Member member = new Member();
        member.setName("hello");
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        /*When[1]: try catch 문 / 문제점: isEqualTo 메서드의 매개변수가 Expecting과 한글자라도 다르면 오류 발생
        try {
            memberService.join(member1);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        //When[2]
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.(이름이 같으므로)
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}