package io.pinest94.reactivestreams.completion;

import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

public class AcceptCompletion extends Completion {
    public Consumer<ResponseEntity<String>> con;
    public AcceptCompletion(Consumer<ResponseEntity<String>> con) {
        this.con = con;
    }

    @Override
    void run(ResponseEntity<String> value) {
        con.accept(value);
    }
}
