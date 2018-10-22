package com.spring.board_pjt_command;

import com.spring.board_pjt_dao.BDao;
import com.spring.board_pjt_dto.BDto;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BReplyViewCommend implements BCommend {
    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String bId = request.getParameter("bId");

        BDao dao = new BDao();
        BDto dto = dao.replyView(bId);

        model.addAttribute("replyView", dto);
    }
}
