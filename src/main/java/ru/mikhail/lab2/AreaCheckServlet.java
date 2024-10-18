package ru.mikhail.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static ru.mikhail.lab2.DotChecker.checkDot;

@WebServlet(name = "AreaCheckServlet", value = "/area-check-servlet")
public class AreaCheckServlet extends HttpServlet {

    private static final long MIN_EXECUTION_TIME_NS = 1;
    private static final Logger logger = Logger.getLogger(AreaCheckServlet.class.getName());

    @Override
    public void init() throws ServletException {
        if (getServletContext().getAttribute("resultList") == null) {
            getServletContext().setAttribute("resultList", new ArrayList<ResultDto>());
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Dto dto = (Dto) request.getAttribute("dto");
            if (dto == null) {
                throw new IllegalArgumentException("Отсутствует dto аргумент");
            }

            logger.info("Получена dto\n" + dto);

            long startTime = System.nanoTime();
            boolean result = checkDot(dto.getX(), dto.getY(), dto.getR());
            long endTime = System.nanoTime();

            long executionTime = Math.max(endTime - startTime, MIN_EXECUTION_TIME_NS);
            String formattedNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            List<ResultDto> resultList = getResultListFromContext();
            ResultDto resultDto = new ResultDto(dto.getX(), dto.getY(), dto.getR(), executionTime, formattedNow, result);
            resultList.add(resultDto);

            logger.info("Добавлен результат\n" + resultDto + "\nПереадресация на страницу результата");

            response.sendRedirect(request.getContextPath() + "/result.jsp");
        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }



    @SuppressWarnings("unchecked")
    private List<ResultDto> getResultListFromContext() {
        List<ResultDto> resultList = (List<ResultDto>) getServletContext().getAttribute("resultList");
        if (resultList == null) {
            resultList = new ArrayList<>();
            getServletContext().setAttribute("resultList", resultList);
        }
        return resultList;
    }
}
