package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractData implements Comparable<AbstractData> {
    protected int id;

    @Override
    public int compareTo(AbstractData o) {
        return this.id - o.id;
    }
}
