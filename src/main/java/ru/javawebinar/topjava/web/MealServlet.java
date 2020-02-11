package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.MemoryMealStorage;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final MealStorage mealStorage = MemoryMealStorage.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "/meals.jsp";

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealStorage.delete(id);
            log.debug("deleted meal " + id);
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealStorage.get(id);
            forward = "/editmeal.jsp";
            request.setAttribute("mealTo", meal);
            log.debug("forward to edit meals");
        } else if ("add".equals(action)) {
            Meal meal = new Meal(-1, LocalDateTime.now(), "", 0);
            forward = "/editmeal.jsp";
            request.setAttribute("mealTo", meal);
            log.debug("forward to add meals");
        } else {
            List<Meal> meals = mealStorage.getAll();
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealsTo);
            log.debug("forward to meals");
        }

        request.setAttribute("formatter", TimeUtil.getDateTimeFormatter());
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"), TimeUtil.getDateTimeFormatter());
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            mealStorage.edit(id, dateTime, description, calories);
            log.debug("updated meal " + id);
        } else if ("add".equals(action)) {
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"), TimeUtil.getDateTimeFormatter());
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            mealStorage.add(dateTime, description, calories);
            log.debug("added meal");
        }

        resp.sendRedirect("meals");
    }
}
