package io.pinest94.reactivestreams.completion;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

public class Completion<S, T> {
    Consumer<ResponseEntity<String>> con;
    Completion next;

    public Completion() {}

    public void andAccept(Consumer<T> con) {
        Completion<T, Void> c = new AcceptCompletion<T>(con);
        this.next = c;
    }

    public <V> Completion<T, V> andApply(
            Function<T, ListenableFuture<V>> fn) {
        Completion<T, V> c = new ApplyCompletion(fn);
        this.next = c;
        return c;
    }

    public Completion<T, T> andError(Consumer<Throwable> econ) {
        Completion c = new ErrorCompletion(econ);
        this.next = c;
        return c;
    }

    public static <S, T> Completion<S, T> from(ListenableFuture<T> listenableFuture) {
        Completion<S, T> c = new Completion();
        listenableFuture.addCallback(s -> c.complete(s), e -> c.error(e));
        return c;
    }

    protected void error(Throwable e) {
        if(next != null) next.error(e);
    }

    protected void complete(T s) {
        if (next != null) { next.run(s); }
    }

    void run(S value) {}
}