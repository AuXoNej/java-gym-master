package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        List<TrainingSession> sessions = timetable
            .computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>())
            .computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>());

        if (sessions.contains(trainingSession)) {
            return;
        }

        sessions.add(trainingSession);
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
        return new ArrayList<>();
    }

    public List<Pair<String, Integer>> getCountByCoaches() {
        Map<String, Integer> coachCounts = new HashMap<>();

        for (var trainingSession : getAllTrainingSessions()) {
            coachCounts.merge(trainingSession.getCoach().getFullName(), 1, Integer::sum);
        }

        List<Pair<String, Integer>> countTrainingSessions = new ArrayList<>();
        for (var coachCount : coachCounts.entrySet()) {
            countTrainingSessions.add(new Pair<>(coachCount.getKey(), coachCount.getValue()));
        }

        countTrainingSessions.sort((a, b) -> {
            if (!a.getRight().equals(b.getRight())) {
                return Integer.compare(b.getRight(), a.getRight());
            }
            return a.getLeft().compareTo(b.getLeft());
        });

        return countTrainingSessions;
    }
}
