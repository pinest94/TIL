package io.pinest94.reactivestreams.completion;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

public class Completion {
    Consumer<ResponseEntity<String>> con;
    Completion next;

    public Completion() {}

    public void andAccept(Consumer<ResponseEntity<String>> con) {
        Completion c = new AcceptCompletion(con);
        this.next = c;
    }

    public Completion andApply(
            Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        Completion c = new ApplyCompletion(fn);
        this.next = c;
        return c;
    }

    public Completion andError(Consumer<Throwable> econ) {
        Completion c = new ErrorCompletion(econ);
        this.next = c;
        return c;
    }

    public static Completion from(ListenableFuture<ResponseEntity<String>> listenableFuture) {
        Completion c = new Completion();
        listenableFuture.addCallback(s -> c.complete(s), e -> c.error(e));
        return c;
    }

    protected void error(Throwable e) {
        if(next != null) next.error(e);
    }

    protected void complete(ResponseEntity<String> s) {
        if (next != null) { next.run(s); }
    }

    void run(ResponseEntity<String> value) {}
}