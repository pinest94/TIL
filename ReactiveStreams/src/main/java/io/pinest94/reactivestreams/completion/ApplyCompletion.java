package io.pinest94.reactivestreams.completion;

import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

public class ApplyCompletion<S, T> extends Completion<S, T> {
    Function<S, ListenableFuture<T>> fn;
    public ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
        this.fn = fn;
    }

    @Override
    void run(S value) {
        ListenableFuture<T> lf = fn.apply(value);
        lf.addCallback(s->complete(s), e->error(e));
    }
}
