<%@ page import="ru.mikhail.lab2.ResultList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>ЛР №2</title>
    <link rel="stylesheet" href="css/site.css" type="text/css">
    <script defer src="js/index.js"></script>
</head>
<body>

<header>Долинный Михаил Владимирович, P3232, 99438</header>
<audio id="intro_audio" src="audio/site-intro.mp3" autoplay></audio>
<table>
    <tr>
        <!-- Графика (SVG) в первой ячейке -->
        <td id="coordinate-plate">
            <svg width="500" height="500" xmlns="http://www.w3.org/2000/svg" id="plate">
                <!-- Ось X -->
                <line id="axis-x" x1="50" y1="250" x2="450" y2="250" stroke="silver" stroke-width="2"></line>
                <!-- Ось Y -->
                <line id="axis-y" x1="250" y1="50" x2="250" y2="450" stroke="silver" stroke-width="2"></line>

                <!-- Стрелки -->
                <polygon id="arrow-x" points="450,245 450,255 460,250" fill="silver"></polygon>
                <polygon id="arrow-y" points="245,50 255,50 250,40" fill="silver"></polygon>

                <!-- Прямоугольник в 3 четверти -->
                <rect id="rect" x="200" y="251" width="49" height="99" fill="white"></rect>
                <!-- 1/4 окружности в 4 четверти -->
                <path id="arc" d="M 350 251 A 75 100 400 0 1 251 350 L 251 251 Z" fill="white"></path>
                <!-- Треугольник в 1 четверти -->
                <polygon id="triangle" points="251,249 251,200 350,249" fill="white"></polygon>

                <text x="260" y="50" id="text-y" fill="white">Y</text>
                <text x="450" y="240" id="text-x" fill="white">X</text>

                <!--Метки радиуса-->
                <line id="mark-neg-rx" x1="150" y1="245" x2="150" y2="255" stroke="silver" stroke-width="2"></line>
                <line id="mark-rx" x1="350" y1="245" x2="350" y2="255" stroke="silver" stroke-width="2"></line>
                <line id="mark-ry" x1="245" y1="150" x2="255" y2="150" stroke="silver" stroke-width="2"></line>
                <line id="mark-neg-ry" x1="245" y1="350" x2="255" y2="350" stroke="silver" stroke-width="2"></line>

                <text x="130" y="245" id="label-neg-rx" class="small" fill="white">-R</text>
                <text x="353" y="245" id="label-rx" class="small" fill="white">R</text>
                <text x="260" y="360" id="label-neg-ry" class="small" fill="white">-R</text>
                <text x="260" y="154" id="label-ry" class="small" fill="white">R</text>


        <%
    List<ResultList> resultList = (List<ResultList>) application.getAttribute("resultList");
    if (resultList != null) {
        for (ResultList result1 : resultList) {
            if (result1.getResult()){%>
                <circle cx=<%=250+ 20 *result1.getX()%> cy=<%=250 - 20*result1.getY()%> r="2" fill="green" visibility="visible"></circle>
                <%
                        }
            else { %>
                <circle cx=<%=250+ 20 *result1.getX()%> cy=<%=250 - 20*result1.getY()%> r="2" fill="red" visibility="visible"></circle>
        <%}
                        }
                    }%>
            </svg>







        </td>

        <!-- Форма в отдельной строке, в одной ячейке -->
        <td id="input">

            <div id="error" hidden></div>
            <form action="/" method="GET" id="data-form">
                <fieldset id="legend-x">
                    <label for="x">Введите X (от -3 до 3):</label>
                    <input type="text" id="x" name="x" required>

                </fieldset>

                <!-- Ввод Y -->
                <fieldset id="legend-y">
                    <label for="y">Выберите Y:</label>
                    <select id="y" name="y" required>
                        <option value="-4">-4</option>
                        <option value="-3">-3</option>
                        <option value="-2">-2</option>
                        <option value="-1">-1</option>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                    </select>


                </fieldset>
                <!-- Выбор радиуса -->
                <fieldset id="legend-r">
                    <legend> Выберите радиус R:</legend>

                    <label><input type="radio" name="r" value="1"> 1</label>
                    <label><input type="radio" name="r" value="2"> 2</label>
                    <label><input type="radio" name="r" value="3"> 3</label>
                    <label><input type="radio" name="r" value="4"> 4</label>
                    <label><input type="radio" name="r" value="5" checked> 5</label>

                </fieldset>

                <!-- Кнопка отправки -->
                <button id="submit" type="submit">Проверить</button>
            </form>
            <audio id="false_audio" src="audio/ne-very.mp3"></audio>
            <audio id="true_audio" src="audio/sladki-snov.mp3"></audio>
        </td>

        <td id="result">
            <table id="result-table">
                <tr>
                    <th id="result-x">X</th>
                    <th id="result-y">Y</th>
                    <th id="result-r">R</th>
                    <th id="result-time">Time</th>
                    <th id="result-now">Now</th>
                    <th id="result-answer">Result</th>
                </tr>
                <%

                    if (resultList != null) {
                        for (ResultList result : resultList) {
                %>
                <tr>
                    <td><%=result.getX()%>
                    </td>
                    <td><%=result.getY()%>
                    </td>
                    <td><%=result.getR()%>
                    </td>
                    <td><%=result.getCompleteTime()%> нс
                    </td>
                    <td><%=result.getTime()%>
                    </td>

                    <td><%=result.getResult()%>
                    </td>
                        <%
                            }
                        }%>

            </table>
        </td>
    </tr>
</table>

</body>
</html>
