package ru.mikhail.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.mikhail.lab2.DotChecker.checkDot;


@WebServlet(name = "AreaCheckServlet", value = "/area-check-servlet")
public class AreaCheckServlet extends HttpServlet {

    public void init() throws ServletException {

        if (getServletContext().getAttribute("resultList") == null) {
            getServletContext().setAttribute("resultList", new ArrayList<ResultDto>());

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dto dto = (Dto) request.getAttribute("dto");
        boolean result = checkDot(dto.getX(), dto.getY(), dto.getR());

        List<ResultDto> resultList = (List<ResultDto>) getServletContext().getAttribute("resultList");
        ResultDto resultDto = new ResultDto(dto.getX(), dto.getY(), dto.getR(), result);
        resultList.add(resultDto);

        response.sendRedirect(request.getContextPath() + "/result.jsp");


    }


}

