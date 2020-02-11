package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
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
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage mealStorage;

    @Override
    public void init() throws ServletException {
        mealStorage = MemoryMealStorage.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = null;

        String action = request.getParameter("action");
        if (action == null) action = "list";
        switch (action) {
            case "edit": {
                Integer id = Integer.valueOf(request.getParameter("id"));
                Meal meal = mealStorage.get(id);
                forward = "/editmeal.jsp";
                request.setAttribute("mealTo", meal);
                log.debug("forward to edit meals");
                break;
            }
            case "add": {
                Meal meal = new Meal(null, LocalDateTime.now(), "", 0);
                forward = "/editmeal.jsp";
                request.setAttribute("mealTo", meal);
                log.debug("forward to add meals");
                break;
            }
            case "list": {
                List<Meal> meals = mealStorage.getAll();
                List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("meals", mealsTo);
                forward = "/meals.jsp";
                log.debug("forward to meals");
            }
        }

        request.setAttribute("formatter", TimeUtil.DATE_TIME_FORMATTER);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if ("edit".equals(action) || "add".equals(action)) {
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"), TimeUtil.DATE_TIME_FORMATTER);
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            String idString = req.getParameter("id");
            Integer id = (idString == "") ? null : Integer.valueOf(idString);
            Meal meal = new Meal(id, dateTime, description, calories);

            if ("edit".equals(action)) {
                mealStorage.edit(meal);
                log.debug("updated meal " + id);
            } else {
                mealStorage.add(meal);
                log.debug("added meal");
            }
        }

        if ("delete".equals(action)) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            mealStorage.delete(id);
            log.debug("deleted meal " + id);
        }

        resp.sendRedirect("meals");
    }
}
