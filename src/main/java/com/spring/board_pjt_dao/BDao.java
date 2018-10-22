package com.spring.board_pjt_dao;

import com.spring.board_pjt_dto.BDto;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class BDao implements AutoCloseable {

    DataSource dataSource;

    public BDao() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
    }

    //글 쓰기
    public void write(String bName, String bTitle, String bContent){
        String query = "insert into spring_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (springBoard_seq.nextval, ?, ?, ?, 0, springBoard_seq.currval, 0, 0)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bName);
            preparedStatement.setString(2, bTitle);
            preparedStatement.setString(3, bContent);
            preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //글 목록 불러오기
    public ArrayList<BDto> list() {
        String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from spring_board order by bGroup desc, bStep asc";

        ArrayList<BDto> dtos = new ArrayList();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int bId = resultSet.getInt("bId");
                String bName = resultSet.getString("bName");
                String bTitle = resultSet.getString("bTitle");
                String bContent = resultSet.getString("bContent");
                Timestamp bDate = resultSet.getTimestamp("bDate");
                int bHit = resultSet.getInt("bHit");
                int bGroup = resultSet.getInt("bGroup");
                int bStep = resultSet.getInt("bStep");
                int bIndent = resultSet.getInt("bIndent");

                BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
                dtos.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }

    //글 보기
    public BDto contentView(String strID){
        upHit(strID);

        String query = "select * from spring_board where bId = ?";
        BDto dto = null;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
            ) {

            preparedStatement.setInt(1, Integer.parseInt(strID));
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int bId = resultSet.getInt("bId");
                    String bName = resultSet.getString("bName");
                    String bTitle = resultSet.getString("bTitle");
                    String bContent = resultSet.getString("bContent");
                    Timestamp bDate = resultSet.getTimestamp("bDate");
                    int bHit = resultSet.getInt("bHit");
                    int bGroup = resultSet.getInt("bGroup");
                    int bStep = resultSet.getInt("bStep");
                    int bIndent = resultSet.getInt("bIndent");

                    dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return dto;
    }

    //조회수 증가
    private void upHit(String strID){
        String query = "update spring_board set bHit = bHit + 1 where bId = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,Integer.parseInt(strID));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //글 수정
    public void modify(String strID, String bName, String bTitle, String bContent){
        String query = "update spring_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bName);
            preparedStatement.setString(2, bTitle);
            preparedStatement.setString(3, bContent);
            preparedStatement.setInt(4, Integer.parseInt(strID));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //글 삭제
    public void delete(String strID){
        String query ="delete from SPRING_BOARD where bId = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(strID));
            preparedStatement.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //답글 달기
    public void reply(String strId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent){
        String query = "insert into spring_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (springBoard_seq.nextval, ?, ?, ?, ?, ?, ?)";

        replyShape(bGroup,bStep);

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bName);
            preparedStatement.setString(2, bTitle);
            preparedStatement.setString(3, bContent);
            preparedStatement.setInt(4, Integer.parseInt(bGroup));
            preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
            preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void replyShape(String srtGroup, String srtStep){
        String query = "update spring_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,Integer.parseInt(srtGroup));
            preparedStatement.setInt(2,Integer.parseInt(srtStep));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //답글 보기
    public BDto replyView(String strID){
        String query = "select * from SPRING_BOARD where bId = ?";
        BDto dto = null;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,Integer.parseInt(strID));
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    int bId = resultSet.getInt("bId");
                    String bName = resultSet.getString("bName");
                    String bTitle = resultSet.getString("bTitle");
                    String bContent = resultSet.getString("bContent");
                    Timestamp bDate = resultSet.getTimestamp("bDate");
                    int bHit = resultSet.getInt("bHit");
                    int bGroup = resultSet.getInt("bGroup");
                    int bStep = resultSet.getInt("bStep");
                    int bIndent = resultSet.getInt("bIndent");

                    dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }
}

