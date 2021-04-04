package com.example.ex03.sample.controller;


import com.example.ex03.sample.dto.SampleDTO;
import com.example.ex03.security.dto.ClubAuthMemeberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/ex1")
    public void ex1(){
        log.info("ex1.html........................");
    }


    @GetMapping({"/ex2", "/exLink"})
    public void exModel(Model model){
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("First.." + i)
                    .last("Last.." + i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }


    @GetMapping("/exInline")
    public String exInline(RedirectAttributes redirectAttributes){

        SampleDTO dto = SampleDTO.builder()
                        .sno(100L)
                        .first("First...100")
                        .last("Last...100")
                        .regTime(LocalDateTime.now())
                        .build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);
        return "redirect:/sample/ex3";
    }


    @GetMapping("/ex3")
    public void ex3(){
        log.info("....ex3");
    }


    @GetMapping({"/exLayout1", "/exLayout2", "/exTemplate","/exSidebar"})
    public void exLayout1(){
        log.info("exLayout.......");
    }


    // 권한이 있는 사용자더라도, 특정 사용자인 user95@example.com 만, 이 컨트롤러에 접근 할 수 있다는 의미.
    @PreAuthorize("#clubAuthMemeberDTO != null && #clubAuthMemeberDTO.username eq \"user95@example.com\"")
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemeberDTO clubAuthMemeberDTO){
        log.info("exMember.................");
        log.info("--------------------------");
        log.info(clubAuthMemeberDTO);
    }
}
