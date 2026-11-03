package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableGetCountByCoachesTest {

    @Test
    void testGetCountByCoaches_EmptyTimetable() {
        Timetable timetable = new Timetable();

        List<Pair<String, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetCountByCoaches_OneCoachOneSession() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Бокс", Age.ADULT, 60);
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session);

        List<Pair<String, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Иванов Иван Иванович", result.getFirst().getLeft());
        Assertions.assertEquals(1, result.getFirst().getRight());
    }

    @Test
    void testGetCountByCoaches_OneCoachMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Бокс", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));

        List<Pair<String, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Иванов Иван Иванович", result.getFirst().getLeft());
        Assertions.assertEquals(3, result.getFirst().getRight());
    }

    @Test
    void testGetCountByCoaches_MultipleCoachesOneEach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Фитнес", Age.ADULT, 60);
        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));

        List<Pair<String, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        Map<String, Integer> coachMap = new LinkedHashMap<>();
        for (Pair<String, Integer> pair : result) {
            coachMap.put(pair.getLeft(), pair.getRight());
        }

        Assertions.assertEquals(1, coachMap.get("Иванов Иван Иванович"));
        Assertions.assertEquals(1, coachMap.get("Петров Петр Петрович"));
    }

    @Test
    void testGetCountByCoaches_SortingByCount() {
        Timetable timetable = new Timetable();

        Group group = new Group("Фитнес", Age.ADULT, 60);
        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Coach coach3 = new Coach("Сидоров", "Сидор", "Сидорович");

        // Иванов — 3 занятия
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));

        // Петров — 2 занятия
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.THURSDAY, new TimeOfDay(15, 0)));

        // Сидоров — 1 занятие
        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SATURDAY, new TimeOfDay(9, 0)));

        List<Pair<String, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        // Проверить сортировку по убыванию
        Assertions.assertTrue(result.get(0).getRight() >= result.get(1).getRight());
        Assertions.assertTrue(result.get(1).getRight() >= result.get(2).getRight());

        // Проверить конкретные значения
        Assertions.assertEquals(3, result.get(0).getRight());
        Assertions.assertEquals(2, result.get(1).getRight());
        Assertions.assertEquals(1, result.get(2).getRight());
    }

    @Test
    void testGetCountByCoaches_EqualCountSorting() {
        Timetable timetable = new Timetable();

        Group group = new Group("Фитнес", Age.ADULT, 60);
        Coach coach1 = new Coach("Андреев", "Андрей", "Андреевич");
        Coach coach2 = new Coach("Борисов", "Борис", "Борисович");
        Coach coach3 = new Coach("Васильев", "Василий", "Васильевич");
        Coach coach4 = new Coach("Иванов", "Иван", "Иванович");

        // у него 4 занятия
        timetable.addNewTrainingSession(new TrainingSession(group, coach4, DayOfWeek.SUNDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach4, DayOfWeek.MONDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach4, DayOfWeek.THURSDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach4, DayOfWeek.SUNDAY, new TimeOfDay(13, 0)));

        // У всех по 2 занятия
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.THURSDAY, new TimeOfDay(15, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SATURDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SUNDAY, new TimeOfDay(10, 0)));


        List<Pair<String, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertEquals(4, result.size());

        Assertions.assertEquals(4, result.get(0).getRight());
        Assertions.assertEquals(2, result.get(1).getRight());
        Assertions.assertEquals(2, result.get(2).getRight());
        Assertions.assertEquals(2, result.get(3).getRight());

        // Проверяем порядок по алфавиту
        Assertions.assertEquals("Андреев Андрей Андреевич", result.get(1).getLeft());
        Assertions.assertEquals("Борисов Борис Борисович", result.get(2).getLeft());
        Assertions.assertEquals("Васильев Василий Васильевич", result.get(3).getLeft());

    }

    @Test
    void testGetCountByCoaches_NoDuplicateSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Бокс", Age.ADULT, 60);
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session);
        timetable.addNewTrainingSession(session);

        List<Pair<String, Integer>> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Иванов Иван Иванович", result.getFirst().getLeft());
        Assertions.assertEquals(1, result.getFirst().getRight());
    }
}
