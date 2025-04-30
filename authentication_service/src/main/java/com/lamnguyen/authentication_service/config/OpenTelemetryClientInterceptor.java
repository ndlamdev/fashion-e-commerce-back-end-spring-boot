package com.lamnguyen.authentication_service.config;

import io.grpc.*;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapSetter;
import org.springframework.stereotype.Component;

@Component
public class OpenTelemetryClientInterceptor implements ClientInterceptor {

    private static final TextMapSetter<Metadata> METADATA_SETTER = new TextMapSetter<>() {
        @Override
        public void set(Metadata metadata, String key, String value) {
            if (key != null && value != null) {
                metadata.put(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER), value);
            }
        }
    };

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {

        Metadata metadata = new Metadata();
        GlobalOpenTelemetry.getPropagators()
                .getTextMapPropagator()
                .inject(Context.current(), metadata, METADATA_SETTER);

        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.merge(metadata);
                super.start(responseListener, headers);
            }
        };
    }
}