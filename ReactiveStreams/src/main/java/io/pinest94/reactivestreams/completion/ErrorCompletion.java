package io.pinest94.reactivestreams.completion;

import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;

public class ErrorCompletion<T> extends Completion<T, T> {
    public Consumer<Throwable> econ;

    public ErrorCompletion(Consumer<Throwable> econ) {
        this.econ = econ;
    }

    @Override
    void run(T value) {
        if (next != null) { next.run(value); }
    }
}
