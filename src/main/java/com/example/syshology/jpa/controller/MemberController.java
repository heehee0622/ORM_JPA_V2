package com.example.syshology.jpa.controller;

import com.example.syshology.jpa.entity.Address;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.model.MemberForm;
import com.example.syshology.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public ModelAndView createForm(ModelAndView model) {
        model.addObject("memberForm", new MemberForm());
        model.setViewName("members/createMemberForm");
        return model;
    }

    @PostMapping(value = "/members/new")
    public void create(@Valid MemberForm form, BindingResult result, HttpServletResponse response) throws IOException {
        if (result.hasErrors()) {
            response.sendRedirect("members/createMemberForm");
        }
        Address address = new Address(form.getCity(), form.getStreet(),
                form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        response.sendRedirect("/");
    }
    //추가
    @GetMapping(value = "/members")
    public ModelAndView list(ModelAndView model) {
        List<Member> members = memberService.findMembers();
        model.addObject("members", members);
        model.setViewName("members/memberList");
        return model;
    }
}