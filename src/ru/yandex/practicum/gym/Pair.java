package ru.yandex.practicum.gym;

public record Pair<L, R>(L key, R value) {


    public L getLeft() {
        return key;
    }

    public R getRight() {
        return value;
    }

}
