<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.mikhail.lab2.ResultDto" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Результаты</title>
    <link rel="stylesheet" href="css/result.css" type="text/css">
</head>
<body>

<header>Результат проверки</header>
<audio id="intro_audio" src="audio/CristoforoColombo.mp3" autoplay></audio>

<table id="result">
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Time</th>
        <th>Now</th>
        <th>Result</th>
    </tr>

    <%
        List<ResultDto> resultList = (List<ResultDto>) application.getAttribute("resultList");
        if (resultList != null && !resultList.isEmpty()) {
            ResultDto lastResult = resultList.get(resultList.size() - 1);
    %>
    <tr>
        <td><%= lastResult.getX() %>
        </td>
        <td><%= lastResult.getY() %>
        </td>
        <td><%= lastResult.getR() %>
        </td>
        <td><%= lastResult.getCompleteTime() %> нс
        </td>
        <td><%= lastResult.getTime() %>
        </td>

        <td><%= lastResult.getResult() %>
        </td>
    </tr>
    <%
    } else {
    %>
    <tr>
        <td colspan="4">Нет доступных результатов</td>
    </tr>
    <%
        }
    %>
</table>

<a id="return" href="index.jsp">Назад</a>

</body>
</html>
