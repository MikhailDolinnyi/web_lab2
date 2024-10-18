package ru.mikhail.lab2;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ControllerServlet", value = "/controller-servlet")
public class ControllerServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ControllerServlet.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Validator validator = new Validator();
        Dto dto = new Dto();
        logger.info("Создание Logger и Dto");
        try {
            parseAndSetParameters(request, dto);
            logger.info("Парсинг параметров прошёл успешно");
            validator.validate(dto.getX(), dto.getY(), dto.getR());
            logger.info("Валидация прошла успешно");
            request.setAttribute("dto", dto);
        } catch (ValidateException e) {
            logger.warning(e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        } catch (NullPointerException | NumberFormatException e) {
            logger.warning("Неверный формат одной или нескольких переменных, либо отсутствуют значения");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат одной или нескольких переменных, либо отсутствуют значения");
            return;
        }


        logger.info("Переадресация на area-check-servlet");
        // Перенаправление на AreaCheckServlet
        getServletContext().getRequestDispatcher("/area-check-servlet").forward(request, response);


    }


    private void parseAndSetParameters(HttpServletRequest request, Dto dto) {
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        dto.setAll(Float.parseFloat(x), Integer.parseInt(y), Integer.parseInt(r));
    }

    public void destroy() {
    }
}
