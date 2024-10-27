package ru.mikhail.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(name = "RequestInfoServlet", value = "/request-info-servlet")
public class RequestInfoServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RequestInfoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем список запросов из контекста
        List<Map<String, Object>> requestInfoList = (List<Map<String, Object>>) getServletContext().getAttribute("requestInfoList");

        if (requestInfoList == null || requestInfoList.isEmpty()) {
            // Если данных нет, возвращаем сообщение об отсутствии данных
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.getWriter().write("{\"message\": \"Данные о запросах отсутствуют\"}");
        } else {
            // Возвращаем список запросов в виде JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.println("[");
            for (int i = 0; i < requestInfoList.size(); i++) {
                Map<String, Object> requestInfo = requestInfoList.get(i);
                out.println("{");
                out.println("\"timestamp\": \"" + requestInfo.get("timestamp") + "\",");
                out.println("\"isValid\": " + requestInfo.get("isValid"));
                out.println(i < requestInfoList.size() - 1 ? "}," : "}");
            }
            out.println("]");
        }
    }
}
