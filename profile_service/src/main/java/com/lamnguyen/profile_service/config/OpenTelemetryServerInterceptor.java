package com.lamnguyen.profile_service.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import org.springframework.stereotype.Component;

@Component
public class OpenTelemetryServerInterceptor implements ServerInterceptor {

	private static final TextMapGetter<Metadata> METADATA_GETTER = new TextMapGetter<>() {
		@Override
		public Iterable<String> keys(Metadata metadata) {
			return metadata.keys();
		}

		@Override
		public String get(Metadata metadata, String key) {
			return metadata.get(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER));
		}
	};

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
			ServerCall<ReqT, RespT> call,
			Metadata headers,
			ServerCallHandler<ReqT, RespT> next) {

		Context context = GlobalOpenTelemetry.getPropagators()
				.getTextMapPropagator()
				.extract(Context.current(), headers, METADATA_GETTER);

		try (Scope ignored = context.makeCurrent()) {
			return next.startCall(call, headers);
		}
	}
}
