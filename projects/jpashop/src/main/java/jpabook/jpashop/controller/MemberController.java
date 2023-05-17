package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jdt.internal.compiler.env.IUpdatableModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createFrom(@ModelAttribute MemberForm memberForm) {
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid @ModelAttribute MemberForm memberForm, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String members(Model model) {
        //API를 개발할때는 절대 엔티티를 반환하면 안된다 -> DTO를 사용
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";

    }
}
