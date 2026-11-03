package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            if (timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
                if (!timetable
                    .get(trainingSession.getDayOfWeek())
                    .get(trainingSession.getTimeOfDay())
                    .contains(trainingSession)
                ) {
                    timetable
                        .get(trainingSession.getDayOfWeek())
                        .get(trainingSession.getTimeOfDay())
                        .add(trainingSession);
                }
            } else {
                timetable.get(
                    trainingSession.getDayOfWeek()
                ).put(trainingSession.getTimeOfDay(), new ArrayList<>(List.of(trainingSession)));
            }
        } else {
            TreeMap<TimeOfDay, List<TrainingSession>> training = new TreeMap<>();
            training.put(trainingSession.getTimeOfDay(), new ArrayList<>(List.of(trainingSession)));
            timetable.put(trainingSession.getDayOfWeek(), training);
        }
    }

    @Override
    public String toString() {
        if (timetable.isEmpty()) {
            return "В расписании нет занятий";
        } else {
            StringBuilder result = new StringBuilder();
            for (var day : timetable.keySet()) {
                result.append(day.toString()).append("\n");
                for (var time : timetable.get(day).navigableKeySet()) {
                    result.append("\t")
                        .append(time.toString()).append(" - ")
                        .append(timetable.get(day).get(time).toString())
                        .append("\n");
                }
            }
            return result.toString();
        }
    }

    public List<TrainingSession> getAllTrainingSessions() {
        ArrayList<TrainingSession> result = new ArrayList<>();
        for (var day : timetable.keySet()) {
            for (var time : timetable.get(day).keySet()) {
                result.addAll(timetable.get(day).get(time));
            }
        }
        return result;
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek).get(timeOfDay);
        }
        return null;
    }

    public List<Pair<String, Integer>> getCountByCoaches() {
        List<Pair<String, Integer>> countTrainingSessions = new ArrayList<>();

        for (var trainingSession : getAllTrainingSessions()) {

            var index = getIndexCoachInList(countTrainingSessions, trainingSession.getCoach().getFullName());

            if (index != -1) {
                var count = countTrainingSessions.get(index).getRight() + 1;
                var countByCoaches = countTrainingSessions.get(index);

                if (index > 0) {
                    while (index > 0 && countTrainingSessions.get(index - 1).getRight() <= count) {
                        if (countTrainingSessions.get(index - 1).getRight() == count) {
                            if (countTrainingSessions.get(index - 1).getLeft()
                                .compareTo(countByCoaches.getLeft()) < 0
                            ) {
                                break;
                            }
                        }
                        countTrainingSessions.set(index, countTrainingSessions.get(index - 1));
                        countTrainingSessions.set(index - 1, null);
                        index--;

                    }
                }
                countTrainingSessions.set(index, new Pair<>(countByCoaches.getLeft(), count));
            } else {
                int newIndex = countTrainingSessions.size();
                for (int i = 0; i < countTrainingSessions.size(); i++) {
                    if (countTrainingSessions.get(i).getRight() == 1) {
                        if (countTrainingSessions.get(i).getLeft()
                            .compareTo(trainingSession.getCoach().getFullName()) > 0
                        ) {
                            newIndex = i;
                            break;
                        }
                    }
                }
                countTrainingSessions = shiftRightAfterN(countTrainingSessions, newIndex);
                countTrainingSessions.set(newIndex, new Pair<>(trainingSession.getCoach().getFullName(), 1));

            }
        }

        return countTrainingSessions;
    }


    private int getIndexCoachInList(List<Pair<String, Integer>> list, String coachName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLeft().equals(coachName)) {
                return i;
            }
        }
        return -1;
    }

    private static <T> List<T> shiftRightAfterN(List<T> list, int N) {
        List<T> result = new ArrayList<>(list.size() + 1);

        for (int i = 0; i < N && i < list.size(); i++) {
            result.add(list.get(i));
        }

        result.add(null);

        for (int i = N; i < list.size(); i++) {
            result.add(list.get(i));
        }

        return result;
    }
}
