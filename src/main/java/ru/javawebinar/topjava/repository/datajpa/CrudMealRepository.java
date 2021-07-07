package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Modifying
    @Query("UPDATE Meal m set m.user=:userId where m.id=:id")
    Meal save(@Param("id") Integer id, @Param("userId") Integer userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(Integer userId);

    Meal findByIdAndUserId(Integer id, Integer userId);

    @Modifying
    Integer deleteByIdAndUserId(Integer id, Integer userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId  AND m.dateTime >= :start AND m.dateTime < :end ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(
            @Param("start") LocalDateTime startDateTime,
            @Param("end") LocalDateTime endDateTime,
            @Param("userId") Integer userId);
}
