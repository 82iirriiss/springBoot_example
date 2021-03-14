package com.example.ex03.guestbook.controller;

import com.example.ex03.guestbook.dto.GuestbookDTO;
import com.example.ex03.common.dto.PageRequestDTO;
import com.example.ex03.common.dto.PageResultDTO;
import com.example.ex03.guestbook.entity.Guestbook;
import com.example.ex03.guestbook.service.GuestbookService;
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
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;


    @GetMapping({"","/","/list"})
    public String list(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        PageResultDTO<GuestbookDTO, Guestbook> resultDto =  service.getList(requestDTO);
        model.addAttribute("result", resultDto);
        return "/guestbook/list";
    }


    @GetMapping("/register")
    public void register(){
    }


    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes attr){

        attr.addFlashAttribute("msg",service.register(dto));
        return "redirect:/guestbook/list";
    }


    @GetMapping({"/read", "/modify"})
    public void get(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }


    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes attr){

        service.remove(gno);
        attr.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes attr){

        service.modify(dto);
        attr.addAttribute("gno", dto.getGno());
        attr.addAttribute("page", requestDTO.getPage());
        attr.addAttribute("type", requestDTO.getType());
        attr.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/guestbook/read";
    }
}
