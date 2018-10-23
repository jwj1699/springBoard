package com.spring.board_pjt_dao;

import com.spring.board_pjt_dto.BDto;
import com.spring.board_pjt_util.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class BDao implements AutoCloseable {

    DataSource dataSource;
    JdbcTemplate template = null;

    public BDao() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        template = Constant.jdbcTemplate;
    }

    @Override
    public void close() throws Exception {
    }

    //글 쓰기
    public void write(final String bName, final String bTitle, final String bContent){
        String query = "insert into spring_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (springBoard_seq.nextval, ?, ?, ?, 0, springBoard_seq.currval, 0, 0)";
        template.update(query, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, bName);
                preparedStatement.setString(2, bTitle);
                preparedStatement.setString(3, bContent);
            }
        });
    }

    //글 목록
    public ArrayList<BDto> list() {
        ArrayList<BDto> dtos = new ArrayList();

        String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from spring_board order by bGroup desc, bStep asc";
        dtos = (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));

        return dtos;
    }

    //글 보기
    public BDto contentView(String strID){
        upHit(strID);

        String query = "select * from spring_board where bId = " + strID;
        return template.queryForObject(query, new BeanPropertyRowMapper<>(BDto.class));
    }

    //조회수 증가
    private void upHit(final String strID){
        String query = "update spring_board set bHit = bHit + 1 where bId = ?";
        template.update(query, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, Integer.parseInt(strID));
            }
        });
    }

    //글 수정
    public void modify(final String strID, final String bName, final String bTitle, final String bContent){
        String query = "update spring_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
        template.update(query, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, bName);
                preparedStatement.setString(2, bTitle);
                preparedStatement.setString(3, bContent);
                preparedStatement.setInt(4, Integer.parseInt(strID));
            }
        });
    }

    //글 삭제
    public void delete(final String strID){
        String query ="delete from SPRING_BOARD where bId = ?";
        template.update(query, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, Integer.parseInt(strID));
            }
        });
    }

    //답글 달기뷰
    public BDto replyView(String strID){
        String query = "select * from SPRING_BOARD where bId = " + strID;
        return template.queryForObject(query, new BeanPropertyRowMapper<>(BDto.class));
    }

    //답글 달기
    public void reply(final String strId, final String bName, final String bTitle, final String bContent, final String bGroup, final String bStep, final String bIndent){
        replyShape(bGroup,bStep);

        String query = "insert into spring_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (springBoard_seq.nextval, ?, ?, ?, ?, ?, ?)";
        template.update(query, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, bName);
                preparedStatement.setString(2, bTitle);
                preparedStatement.setString(3, bContent);
                preparedStatement.setInt(4, Integer.parseInt(bGroup));
                preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
                preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);
            }
        });
    }

    private void replyShape(final String srtGroup, final String srtStep){
        String query = "update spring_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
        template.update(query, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,Integer.parseInt(srtGroup));
                preparedStatement.setInt(2,Integer.parseInt(srtStep));
            }
        });
    }
}

