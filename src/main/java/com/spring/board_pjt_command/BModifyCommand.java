package com.spring.board_pjt_command;

import com.spring.board_pjt_dao.BDao;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BModifyCommand implements BCommend {
    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String bId = request.getParameter("bId");
        String bName = request.getParameter("bName");
        String bTitle = request.getParameter("bTitle");
        String bContent = request.getParameter("bContent");

        BDao dao = new BDao();
        dao.modify(bId, bName, bTitle, bContent);
    }
}
