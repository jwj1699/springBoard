<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table width="500" cellpadding="0" cellspacing="0" border="1">
    <form action="modify" method="post">
        <input type="hidden" name="bId" value="${contentView.bId}">
        <tr>
            <td>번호</td>
            <td>${contentView.bId}</td>
            <td>히트</td>
            <td>${contentView.bHit}</td>
        </tr>
        <tr>
            <td>이름</td>
            <td colspan="4"> <input type="text" name="bName" value="${contentView.bName}"> </td>
        </tr>
        <tr>
            <td>제목</td>
            <td colspan="3"> <input type="text" name="bTitle" value="${contentView.bTitle}"> </td>
        </tr>
        <tr>
            <td>내용</td>
            <td colspan="3"> <textarea rows="10" name="bContent">${contentView.bContent}</textarea></td>
        </tr>
        <tr>
            <td colspan="4"> <input type="submit" value="수정"> &nbsp;&nbsp; <a href="list">목록보기</a> &nbsp;&nbsp; <a href="delete?bId=${contentView.bId}">삭제</a> &nbsp;&nbsp; <a href="reply_view?bId=${contentView.bId}">답변</a></td>
        </tr>

    </form>
</table>

</body>
</html>
