package com.example;

import io.micronaut.runtime.Micronaut;
import io.opentracing.Tracer;
import io.opentracing.contrib.reactor.TracedSubscriber;
import io.opentracing.util.GlobalTracer;
import reactor.core.publisher.Hooks;

public class Application {
    protected static final Tracer tracer = GlobalTracer.get();

    public static void main(String[] args) {
        Hooks.onEachOperator(TracedSubscriber.asOperator(tracer));
        Hooks.onLastOperator(TracedSubscriber.asOperator(tracer));
        Micronaut.run(Application.class, args);
    }
}