package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimetableAddNewTrainingSessionTest {

    @Test
    void testAddNewTrainingSession() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Аэробика", Age.ADULT, 60);
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    }
    @Test
    void testAddNewTrainingSession_DuplicateSession() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Аэробика", Age.ADULT, 60);
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session);
        timetable.addNewTrainingSession(session);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(
            DayOfWeek.MONDAY, new TimeOfDay(10, 0)).size());
    }

}
