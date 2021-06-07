package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Main;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealMemoryRepository;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    private static final int maxCaloriesPerDay = 2000;
    private static final List<Meal> mealList = MealMemoryRepository.getMealList();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("mealList", mealList);

        log.debug("redirect to meals");
        getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
