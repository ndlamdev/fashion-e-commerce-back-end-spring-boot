/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:24 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerMetricAutoConfiguration;

@Configuration
@ImportAutoConfiguration({
    GrpcServerAutoConfiguration.class,
    GrpcServerFactoryAutoConfiguration.class,
    GrpcServerMetricAutoConfiguration.class
})
public class GrpcServerConfig {
    // This class enables gRPC server auto-configuration
}