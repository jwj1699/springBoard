<%--
  Created by IntelliJ IDEA.
  User: WOOJIN
  Date: 2018-10-23
  Time: 오전 1:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table width="500" cellpadding="0" cellspacing="0" border="1">
        <form action="reply" method="post">
            <input type="hidden" name="bId" value="${replyView.bId}">
            <input type="hidden" name="bGroup" value="${replyView.bGroup}">
            <input type="hidden" name="bStep" value="${replyView.bStep}">
            <input type="hidden" name="bIndent" value="${replyView.bIndent}">
            <tr>
                <td>번호</td>
                <td>${replyView.bId}</td>
                <td>히트</td>
                <td>${replyView.bHit}</td>
            </tr>
            <tr>
                <td>이름</td>
                <td colspan="4"> <input type="text" name="bName" value="${replyView.bName}"> </td>
            </tr>
            <tr>
                <td>제목</td>
                <td colspan="3"> <input type="text" name="bTitle" value="${replyView.bTitle}"> </td>
            </tr>
            <tr>
                <td>내용</td>
                <td colspan="3"> <textarea rows="10" name="bContent">${replyView.bContent}</textarea></td>
            </tr>
            <tr>
                <td colspan="4"> <input type="submit" value="답변"> &nbsp;&nbsp; <a href="list">목록보기</a> <a href="delete?bId=${contentView.bId}">삭제</a> &nbsp;&nbsp; </td>
            </tr>

        </form>
    </table>

</body>
</html>
