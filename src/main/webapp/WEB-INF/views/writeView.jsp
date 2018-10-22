<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 작성</title>
</head>
<body>
    <table width="500" cellpadding="0" cellspacing="0" border="1">
        <form action="write" method="post">
            <tr>
                <td>이름</td>
                <td> <input type="text" name="bName" size="50"> </td>
            </tr>
            <tr>
                <td>제목</td>
                <td> <input type="text" name="bTitle" size="50"> </td>
            </tr>
            <tr>
                <td>내용</td>
                <td> <textarea type="text" name="bContent" rows="10"> </textarea> </td>
            </tr>
            <tr>
                <td colspan="2"> <input type="submit" value="입력"> &nbsp;&nbsp; </td>
            </tr>
        </form>
    </table>
</body>
</html>
