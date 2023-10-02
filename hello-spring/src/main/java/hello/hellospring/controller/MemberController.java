package hello.hellospring.controller;
import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    /***[1] 필드 주입: 생성자 없이 필드에 바로 연결시킴- 별로 좋지 않은 방법 나중에 바꿀 수 있는 방법이 아예 없음
    @Autowired private MemberService memberService;

    ***/
    /***[2] 세터 주입: 생성할 떄 값이 고정 되는 편. 근데 누구나 접근해서 바꿀 수 있는 setter 방식은 선호되지 않음
    private MemberService memberService; // 멤버 필드

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    ***/

    //[3] 생성자 주입: 생성자에 @auto 달아줘서 연결시켜줌
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }
}