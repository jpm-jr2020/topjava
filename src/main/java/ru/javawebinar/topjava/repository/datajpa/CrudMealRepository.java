package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    int deleteMealByIdAndUserId(int id, int userId);

    Meal getMealByIdAndUserId(int id, int userId);

    List<Meal> getMealsByUserIdOrderByDateTimeDesc(int userId);

    @Transactional
    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC")
    List<Meal> getMealsBetween(@Param("startDateTime") LocalDateTime dateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("userId") int userId);
}
