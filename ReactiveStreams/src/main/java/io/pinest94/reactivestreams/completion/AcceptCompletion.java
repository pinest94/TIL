package io.pinest94.reactivestreams.completion;

import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

public class AcceptCompletion<S> extends Completion<S, Void> {
    public Consumer<S> con;
    public AcceptCompletion(Consumer<S> con) {
        this.con = con;
    }

    @Override
    void run(S value) {
        con.accept(value);
    }
}
