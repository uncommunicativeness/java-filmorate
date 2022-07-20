package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryAbstractDataStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService extends AbstractDataService<User> {
    public UserService(InMemoryAbstractDataStorage<User> storage) {
        super(storage);
    }

    private void checkUserName(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            data.setName(data.getLogin());
        }
    }

    @Override
    public Optional<User> create(User data) {
        checkUserName(data);
        return super.create(data);
    }

    public List<User> getFriends(User user) {
        return user.getFriends();
    }

    public void addFriend(User user, User friend) {
        user.addFriend(friend);
    }

    public void removeFriend(User user, User friend) {
        user.removeFriend(friend);
    }

    public Set<User> getCommonFriends(User user, User another) {
        Set<User> commonFriends = new HashSet<>(user.getFriends());
        commonFriends.retainAll(another.getFriends());

        return commonFriends;
    }
}
