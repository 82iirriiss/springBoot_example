package com.example.ex03.controller;

import com.example.ex03.dto.BoardDTO;
import com.example.ex03.dto.PageRequestDTO;
import com.example.ex03.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;


    @GetMapping({"","/","/list"})
    public String getList(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        model.addAttribute("result", service.getList(requestDTO));

        return "/board/list";
    }


    @GetMapping("/register")
    public void register(){
        log.info("register get.....");
    }


    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes attr){
        Long bno = service.register(dto);
        attr.addAttribute("msg", bno);

        return "redirect:/board/list";
    }


    @GetMapping({"/read", "modify"})
    public void get(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long bno, Model model){

        model.addAttribute("boardDTO", service.get(bno));
    }


    @PostMapping("/modify")
    public String modify(PageRequestDTO requestDTO, BoardDTO boardDTO, RedirectAttributes attr){

        service.modify(boardDTO);

        attr.addAttribute("page", requestDTO.getPage());
        attr.addAttribute("type", requestDTO.getType());
        attr.addAttribute("keyword", requestDTO.getKeyword());
        attr.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }


    @PostMapping("/remove")
    public String remove(Long bno){
        service.removeWithReplies(bno);
        return "redirect:/board/list";
    }
}
