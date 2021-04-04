package com.example.ex03.movie.controller;


import com.example.ex03.common.dto.PageRequestDTO;
import com.example.ex03.movie.dto.MovieDTO;
import com.example.ex03.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasRole('ADMIN')")
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
        attr.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO requestDTO, Model model){
        log.info("pageRequestDTO:" + requestDTO);
        model.addAttribute("result", service.getList(requestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long mno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model){
        MovieDTO movieDTO = service.getMovie(mno);
        model.addAttribute("dto", movieDTO);
    }
}
