package com.example.ex03.movie.controller;


import com.example.ex03.common.dto.PageRequestDTO;
import com.example.ex03.movie.dto.MovieDTO;
import com.example.ex03.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping("/register")
    public void register(){
    }

    @PostMapping("/register")
    public String registerPost(MovieDTO dto, RedirectAttributes attr){
        Long mno = service.register(dto);
        attr.addFlashAttribute("mno", mno);
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO requestDTO, Model model){
        log.info("pageRequestDTO:" + requestDTO);
//        model.addAttribute("msg", mno);
        model.addAttribute("result", service.getList(requestDTO));
    }
}
