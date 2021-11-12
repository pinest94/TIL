package io.pinest94.reactivestreams.completion;

import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

public class ApplyCompletion extends Completion {
    Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;
    public ApplyCompletion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        this.fn = fn;
    }

    @Override
    void run(ResponseEntity<String> value) {
        ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
        lf.addCallback(s->complete(s), e->error(e));
    }
}
