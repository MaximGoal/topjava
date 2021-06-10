package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealMemService;
import ru.javawebinar.topjava.util.MealService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDeleteServlet extends HttpServlet {
    private static final Logger logger = getLogger(MealDeleteServlet.class);
    final MealService service = new MealMemService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        if (id > service.getLastId())
            logger.debug("meal id is not valid");
        else
            service.delete(id);


        req.setAttribute("mealToList", service.getMealToList());

        logger.debug("meal deleted");
        getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
