package ru.mikhail.lab2;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ControllerServlet", value = "/controller-servlet")
public class ControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Validator validator = new Validator();
        Dto dto = new Dto();
        try {
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String r = request.getParameter("r");

            dto.setAll(Float.parseFloat(x), Integer.parseInt(y), Integer.parseInt(r));
            validator.validate(dto.getX(), dto.getY(), dto.getR());

            request.setAttribute("dto", dto);
        } catch (ValidateException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        } catch (NullPointerException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат одной или нескольких переменных, либо отсутствуют значения");
            return;
        }

        // Перенаправление на AreaCheckServlet
        getServletContext().getRequestDispatcher("/area-check-servlet").forward(request, response);
    }

    public void destroy() {
    }
}
