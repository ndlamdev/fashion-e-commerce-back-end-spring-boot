package com.lamnguyen.profile_service.config.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcExceptionAdvice {
	@GrpcExceptionHandler(ApplicationException.class)
	public StatusException handleResourceNotFoundException(ApplicationException e) {
		Status status = Status.UNKNOWN.withDescription(e.getMessage()).withCause(e);
		Metadata metadata = new Metadata();
		return status.asException(metadata);
	}
}