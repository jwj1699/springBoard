package com.spring.board_pjt_controller;

import com.spring.board_pjt_command.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BController {

    BCommend commend;

    @RequestMapping("/list")
    public String list(Model model){
        System.out.println("list()");

        commend = new BListCommand();
        commend.execute(model);

        return "list";
    }

    @RequestMapping("/write_view")
    public String writeView(Model model){
        System.out.println("writeView()");

        return "writeView";
    }

    @RequestMapping("/write")
    public String write(HttpServletRequest request, Model model){
        System.out.println("write()");

        model.addAttribute("request",request);  //request를 통체로 넣어줌
        commend = new BWriteCommand();
        commend.execute(model);

        return "redirect:list";
    }

    @RequestMapping("/content_view")
    public String contentView(HttpServletRequest request, Model model){
        System.out.println("contentView()");

        model.addAttribute("request",request);
        commend = new BContentCommand();
        commend.execute(model);

        return "contentView";
    }

    @PostMapping("/modify")
    public String modify(HttpServletRequest request, Model model){
        System.out.println("modify()");

        model.addAttribute("request",request);
        commend = new BModifyCommand();
        commend.execute(model);

        return "redirect:list";
    }

    @RequestMapping("/reply")
    public String reply(HttpServletRequest request, Model model){
        System.out.println("reply()");

        model.addAttribute("request",request);  //request를 통체로 넣어줌
        commend = new BReplyCommand();
        commend.execute(model);

        return "redirect:list";
    }

    @RequestMapping("/reply_view")
    public String replyView(HttpServletRequest request, Model model){
        System.out.println("replyView()");

        model.addAttribute("request",request);
        commend = new BReplyViewCommend();
        commend.execute(model);

        return "replyView";
    }

    @RequestMapping("/delete")
    public String delete(HttpServletRequest request, Model model){
        System.out.println("delete()");

        model.addAttribute("request",request);  //request를 통체로 넣어줌
        commend = new BDeleteCommend();
        commend.execute(model);

        return "redirect:list";
    }


}
