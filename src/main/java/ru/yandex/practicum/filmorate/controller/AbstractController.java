package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationErrorResponse;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.StorageData;

import javax.validation.Valid;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@RestController
public abstract class AbstractController<T extends StorageData> {
    protected int nextId = 0;
    protected Map<Integer, T> storage = new TreeMap<>();

    protected int getNextId() {
        return ++nextId;
    }

    void validate(T data) {
    }

    @GetMapping
    public ResponseEntity<Iterable<T>> findAll() {
        return new ResponseEntity<>(storage.values(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<T> create(@Valid @RequestBody T data) {
        validate(data);
        data.setId(getNextId());
        storage.put(data.getId(), data);
        log.info("Объект класса {} успешно создан", data.getClass().getSimpleName());

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<T> update(@Valid @RequestBody T data) {
        if (!storage.containsKey(data.getId())) {
            throw new ValidationException("Отсутствует объект с переданным ID");
        }
        validate(data);
        storage.put(data.getId(), data);
        log.info("Объект класса {} успешно обновлен", data.getClass().getSimpleName());

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new ValidationErrorResponse(e.getBindingResult().getFieldErrors().stream()
                        .map(error -> new ValidationErrorResponse.ValidationError(error.getField(), error.getDefaultMessage()))
                        .peek(error -> log.warn(error.getMessage()))
                        .collect(Collectors.toList())),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleException(ValidationException e) {
        log.warn(e.getMessage());

        return new ResponseEntity<>(
                Map.of("message", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
