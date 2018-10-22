package com.spring.board_pjt_command;

import com.spring.board_pjt_dao.BDao;
import com.spring.board_pjt_dto.BDto;
import org.springframework.ui.Model;

import java.util.ArrayList;

public class BListCommand implements BCommend {
    @Override
    public void execute(Model model) {
        BDao dao = new BDao();
        ArrayList<BDto> dtos = dao.list();

        model.addAttribute("list", dtos);
    }
}
