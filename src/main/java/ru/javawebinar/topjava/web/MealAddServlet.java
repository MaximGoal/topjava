package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealMemoryRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealAddServlet extends HttpServlet {
    private static final Logger logger = getLogger(MealAddServlet.class);
    private static final List<Meal> mealList = MealMemoryRepository.getMealList();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("redirect to meals/add GET");
        getServletContext().getRequestDispatcher("/mealAdd.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal newMeal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")),
                req.getParameter("desc"),
                Integer.parseInt(req.getParameter("calories"))
        );

        mealList.add(newMeal);
        req.setAttribute("mealList", mealList);

        logger.debug("new meal saved");
        getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
