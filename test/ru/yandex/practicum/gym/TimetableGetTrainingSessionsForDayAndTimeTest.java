package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimetableGetTrainingSessionsForDayAndTimeTest {

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
            DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(
            DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(
            DayOfWeek.TUESDAY, new TimeOfDay(14, 0)).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime_MultipleSessionsSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group1 = new Group("Бокс", Age.ADULT, 60);
        Group group2 = new Group("Йога", Age.ADULT, 90);

        TrainingSession session1 = new TrainingSession(group1, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(
            DayOfWeek.MONDAY, new TimeOfDay(10, 0)).size());
    }

}
