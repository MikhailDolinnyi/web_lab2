package ru.mikhail.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static ru.mikhail.lab2.DotChecker.checkDot;


@WebServlet(name = "AreaCheckServlet", value = "/area-check-servlet")
public class AreaCheckServlet extends HttpServlet {

    public void init() throws ServletException {

        if (getServletContext().getAttribute("resultList") == null) {
            getServletContext().setAttribute("resultList", new ArrayList<String>());

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dto dto = (Dto) request.getAttribute("dto");
        boolean result = checkDot(dto.getX(), dto.getY(), dto.getR());

        List<String> resultList = (List<String>) getServletContext().getAttribute("resultList");
        String resultString = "X: " + dto.getX() + ", Y: " + dto.getY() + ", R: " + dto.getR() + ", Result: " + result;
        resultList.add(resultString);
        getServletContext().setAttribute("resultList", resultList);


        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Result:</h1>");
        for (String s : resultList) {
            out.println("<p>" + s + "</p>");
        }
        out.println("</body></html>");

    }


}

