package com.spring.board_pjt_command;

import com.spring.board_pjt_dao.BDao;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BWriteCommand implements BCommend {
    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest)map.get("request");
        String bName = request.getParameter("bName");
        String bTitle = request.getParameter("bTitle");
        String bContent = request.getParameter("bContent");

        BDao dao = new BDao();
        dao.write(bName, bTitle, bContent);
    }
}
