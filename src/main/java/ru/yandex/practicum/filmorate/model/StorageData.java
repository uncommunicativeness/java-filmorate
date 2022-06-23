package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public abstract class StorageData implements Comparable<StorageData> {
    protected int id;

    @Override
    public int compareTo(StorageData o) {
        return this.id - o.id;
    }
}
