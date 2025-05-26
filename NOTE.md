# Chú tích những thứ học được từ dựng án

## Cấu hình RedisTemplate

### Sự khác biệt giữa Jackson2JsonRedisSerializer và GenericJackson2JsonRedisSerializer

| Đặc điểm                       | Jackson2JsonRedisSerializer<T>                        | GenericJackson2JsonRedisSerializer                                                                         |
|--------------------------------|-------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| **Kiểu dữ liệu**               | Cố định một kiểu cụ thể (T)                           | Đa kiểu (generic Object)                                                                                   |
| **Thông tin class trong JSON** | Không lưu                                             | Có lưu                                                                                                     |
| **Khả năng deserialize**       | Phải chỉ định kiểu cụ thể khi lấy ra                  | Tự động nhận biết kiểu gốc                                                                                 |
| **Dùng khi**                   | Redis chỉ lưu một loại object                         | Redis lưu nhiều loại object                                                                                |
| **Hiệu năng**                  | Nhanh hơn, JSON nhỏ hơn                               | Chậm hơn một chút, JSON to hơn do thêm type info                                                           |
| **Độ an toàn khi deserialize** | Dễ dính lỗi `ClassCastException` nếu không xử lý đúng | An toàn hơn khi deserialize đa kiểu nhưng vẫn có thể bị lỗi `ClassCastException` nếu như làm việc với List |
| **Ứng dụng phù hợp**           | RedisTemplate riêng cho từng kiểu                     | RedisTemplate hoặc Spring Cache dùng chung                                                                 |
| **Cách sử dụng**               | Sử dụng để khi tạo 1 template có kiểu cụ thế          | Sử dụng để khi tạo 1 template có kiểu chung chung tương tự như Object                                      |****

### Khi làm việc với thời gian thì cần cấu hình lại ObjectMapper

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}
}
```

## Cấu hình Kafka

### Cấu hình Serializer và Deserializer cho kafka

* Đối với Producer

```yaml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_SERVER:localhost:9092}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

* Đối với Consumer

```yaml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_SERVER:localhost:9092}
    consumer:
      group-id: user-detail
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
```

### Đặt tên cho các package

#### Khi gửi một đối tượng tự định nghĩa nên đặt tên cho package cho các package event để cho consumer có thể map được dự liệu

* Cú pháp đặt tên

```
<Tên muốn đặt>:<Đường dẫn đầy đủ của class đó>
```

* Đối với producer

```yaml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_SERVER:localhost:9092}
    producer:
      properties:
        spring:
          json:
            type:
              mapping: "send_mail_verify:com.lamnguyen.authentication_service.event.SendMailVerifyEvent,save_user_detail:com.lamnguyen.authentication_service.event.SaveUserDetailEvent"
```

```java
package com.lamnguyen.authentication_service.event;

import com.lamnguyen.authentication_service.util.enums.MailType;
import lombok.Builder;

@Builder
public record SendMailVerifyEvent(String email,
                                  String otp,
                                  MailType type) {
}
```

* Đối với consumer

```yaml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_SERVER:localhost:9092}
    consumer:
      properties:
        spring:
          json:
            trusted:
              packages: "save_user_detail"
            type:
              mapping: "save_user_detail:com.lamnguyen.profile_service.message.SaveUserDetailMessage"
```

```java
package com.lamnguyen.profile_service.message;

import com.lamnguyen.profile_service.utils.enums.SexEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveUserDetailMessage {
	Long userId;
	String fullName;
	String phone;
	SexEnum sexEnum;
	LocalDate birthday;
}

```

<span style="color:red">* Chú ý: </span> Riêng với consumer cần cấu hình để cho consumer có thể trust các package mà
được gửi trong kafka và chấp nhận mapping. Có thể thay thành ``*`` nếu như đang trong quá trình dev.

## Luồng đăng nhập bằng Google trong Restfull Api

### ✅ Tổng quan

Luồng xác thực người dùng bằng Google sử dụng Authorization Code bao gồm các bước:

1. **Client** redirect người dùng đến Google để xác thực (flow: `auth-code`*).
2. Người dùng đăng nhập, Google redirect về lại `redirect_uri` kèm theo `auth-code`.
3. **Client** gửi `auth-code` về cho **Server (Backend)**.
4. **Server** sử dụng `code` để lấy `id-token` từ Google.
5. **Server** dùng `id-token` để verify và lấy thông tin người dùng.
6. **Server** tạo hoặc kiểm tra user trong DB → trả JWT hoặc session token cho client.

![Google OAuth2 Flow](https://user-images.githubusercontent.com/29760858/65579674-a4d22c00-df91-11e9-8303-dc97e5bb0dbf.png)

\* `auth-code` là 1 đoạn code do google cấp để cho phía server có thể xác thực người dùng và chỉ được sử dụng 1 lần duy
nhất dù có lỗi xác nhận thì code này cũng sẽ bị hủy.

### Setup google service thông qua firebase

1. Tạo dự án [Firebase](https://console.firebase.google.com/)
2. Sau khi tạo thành công dự án; Tại `Dashboard` chọn `Build` (menu phía bên trái màng hình máy tính)
3. Chọn `Authentication` (Nếu lần đầu sẽ bấm `Get started` để bắt đầu setup)
4. Chọn `Sign-in method` (Nếu lần đầu thì sẽ chuyển đến bước 5 luôn)
5. Chọn `Add new provider`
6. Chọn vào biểu tươợng `Google` (Có thể chọn các dịch vụ khác và setup theo dịch vụ đó)
7. Tạo xong thì copy `client-id` và `client-secret`

\* Không để lộ `client-secret`

### Các thư viện hổ trợ việc xác thực `auth-code` và lấy thông tin người dùng

```groovy
implementation 'com.google.api-client:google-api-client:2.7.2'
```

### Config các thư viện

#### Xác thực `auto-code`

```java

@Bean
public GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest() {
	return new GoogleAuthorizationCodeTokenRequest(
			new NetHttpTransport(),
			new GsonFactory(),
			"https://oauth2.googleapis.com/token",
			"<client-id>",
			"<client-secret>",
			"", // Phần này nên điền chuổi rổng để khi nào muốn xác thực thì set vào
			"postmessage" // or your redirect URI
	);
}
```

#### Xác thực `id-token`

```java

@Bean
public GoogleIdTokenVerifier googleIdTokenVerifier() {
	return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
			.setAudience(Collections.singletonList("<client-id>"))
			.build();
}
```

### Triển khai service

```java
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoogleAuthServiceImpl {
	GoogleIdTokenVerifier googleIdTokenVerifier;
	GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest;

	public GoogleTokenResponse verifyGoogleAuthCode(String authCode) {
		try {
			return googleAuthorizationCodeTokenRequest.setCode(authCode).execute();
		} catch (IOException e) {
			throw ApplicationException.createException(ExceptionEnum.UNAUTHORIZED);
		}
	}

	@Override
	public GoogleIdToken.Payload getPayload(String idTokenString) throws GeneralSecurityException, IOException {
		GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenString);
		if (idToken == null) throw new RuntimeException("token is null");
		return idToken.getPayload();
	}

	public LoginSuccessDto login(String authCode) {
		var response = verifyGoogleAuthCode(authCode);
		var payload = getPayload(response.getIdToken());
		var avatar = payload.get("picture").toString();
		var fullName = payload.get("name").toString();
		// Triển khai theo logic mà bạn mong muốn 
	}
}
```

## Luồng đăng nhập bằng Facebook trong Restfull Api

### ✅ Tổng quan

Luồng xác thực người dùng bằng Google sử dụng Authorization Code bao gồm các bước:

1. **Client** redirect người dùng đến Facebook để `code-code` tài khoản người dùng
2. Người dùng đăng nhập, Facebook redirect về lại `redirect_uri` kèm theo `code`.
3. **Client** gửi `auth-code` về cho **Facebook** yêu cầu 1 `access-token` ngắn hạn.
4. **Client** gửi `access-token` ngắn hạn đó về phía server.
5. **Server** sử dụng `access-token`, gửi 1 lệnh xác thực (thường là debug) để xác thực tính toàn vẹn của token và trả
   về 1 số thông tin như: `user-id`, `app-id`,....
6. **Server** dùng `app-id` để xác thực xem token trên có phải là được tạo ra từ app chúng ta hay không.
7. **Server** dùng `user-id` để tạo hoặc kiểm tra user trong DB → trả JWT hoặc session token cho client.

\* Ghi chú: Bình thường sẽ lấy `auth-code` và gửi về server nhưng vì viết restfull nên điều đó sẽ rất khó khăn. Thay vào
đó chúng ta sẽ lấy 1 `access-token` ngắn hạn từ facebook bằng cách gửi yêu cầu lấy `access-token` ngắn hạn từ phía
client-side. Nên cấu hình để `access-token` này chỉ có thể lấy được các
thông tin public của user để phòng trường hợp lộ token cũng không ảnh hưởng quá nhiều đến tài khoản facebook người dùng.

### Các thư viện hổ trợ

```groovy
    implementation 'org.springframework.cloud:spring-cloud-starter-openfeign'
```

### config openfeign

#### EnableFeignClients

```java

@SpringBootApplication
@EnableFeignClients
public class YourApplication {

	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);
	}

}
```

#### Tạo FeignClients

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "IFacebookGraphClient", url = "https://graph.facebook.com/v22.0/")
public interface IFacebookGraphClient {

	@GetMapping(value = "/debug_token?access_token={access_token}&input_token={access_token}&format=json&method=get&origin_graph_explorer=1&pretty=1&suppress_http_code=1&transport=cors", produces = "application/json")
	DebugTokenResponse debugToken(@RequestParam("access_token") String token);

	@GetMapping(value = "/oauth/access_token?grant_type=fb_exchange_token")
	ExchangeTokenResponse exchangeToken(@RequestParam("client_id") String appId, @RequestParam("client_secret") String appSecret, @RequestParam("fb_exchange_token") String token);

	@GetMapping(value = "/me?fields=id%2Cname%2Cemail%2Cfirst_name%2Clast_name%2Cpicture%2Clocale%2Ctimezone")
	ProfileUserFacebookResponse getProfile(@RequestParam("access_token") String accessToken);

	record DebugTokenResponse(
			DataDebugTokenResponse data
	) {
		public record DataDebugTokenResponse(
				@JsonProperty("app_id")
				String appId,
				String type,
				String application,
				@JsonProperty("data_access_expires_at")
				long dataAccessExpiresAt,
				@JsonProperty("expires_at")
				long expiresAt,
				@JsonProperty("is_valid")
				boolean valid,
				@JsonProperty("user_id")
				String userId,
				String[] scopes
		) {
		}
	}

	record ExchangeTokenResponse(
			@JsonProperty("access_token")
			String accessToken,
			@JsonProperty("token_type")
			String tokenType,
			@JsonProperty("expires_in")
			long expiresIn
	) {
	}

	record ProfileUserFacebookResponse(
			String id,
			String name,
			@JsonProperty("first_name")
			String firstName,
			@JsonProperty("last_name")
			String lastName,
			@JsonProperty("picture")
			Picture avatar
	) {
		public record Picture(
				PictureData data
		) {
			public record PictureData(
					double height,
					double width,
					@JsonProperty("is_silhouette")
					boolean isSilhouette,
					String url
			) {
			}
		}
	}
}
```

## Cấu hình Grpc

### Các thư viện cần phải có phía client và server.

```groovy
plugins {
    id "com.google.protobuf" version "0.9.5"
}

ext {
    springGrpcVersion = "0.7.0"
}

dependencies {
    implementation 'net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE' // for server
    implementation 'net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE' // for client
    implementation 'javax.annotation:javax.annotation-api:1.3.2'
    implementation 'com.google.protobuf:protobuf-java:4.30.2'
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom "org.springframework.grpc:spring-grpc-dependencies:$springGrpcVersion"
    }
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.30.2" // Hoặc bản mới hơn
    }
    plugins {
        grpc {
            artifact = "io.grpc:protoc-gen-grpc-java:1.72.0"
        }
    }
    generateProtoTasks {
        all().each { task ->
            task.plugins {
                grpc {}
            }
        }
    }
}
```

### Tạo file proto

Các file .proto phải được ở folder src/main/proto/*.proto

```protobuf
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.lamnguyen.*.protos";
option java_outer_classname = "HelloWorldProto";

// The greeting service definition.
service HelloWorldService {
  // Sends a greeting
  rpc SayHello (HelloRequest) returns (HelloReply) {
  }
  rpc StreamHello(HelloRequest) returns (stream HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}
```

### Generate code

\* Lưu ý:

- Nên clean trước khi build lại dự án
- Phải có server thực sự chạy với cùng package thì client mới có thể gọi được

### Extends service

```java
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HelloWorldServiceGrpcImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

	@Override
	public void getUserProfile(ProfileUserRequest request, StreamObserver<ProfileUserResponse> responseObserver) {
		var result = ""; // logic get data
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}
}

```

## Cách triển khai 1 server kafka, redis, my-sql từ xa  bằng cloudflared

1. Thiết lập các dịch vụ trước trên các máy tính từ xa
2. Tải và cài đặt cloudflared trên máy tính
   host [window](https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-windows-amd64.msi)
3. install service bằng lệnh  `cloudflared service install <key>`; Kiềm key này bằng cách tạo 1 tunnel và tìm kiếm trong
   phần hướng dẫn trong lúc tạo tunnel
4. Chạy lệnh `cloudflared login`
5. Chạy lệnh tạo tunnel `cloudflared tunnel create <ten_tunnel_muon_tao>`; Lệnh kiểm tra danh dách tunnel
   `clouflared tunnel list`
6. Chạy tunnel `cloudflared tunnel run <ten_tunnel_da_tao>`
7. Truy cập trang web quản lý tunnel của cloudflare
8. Chọn tunnel bạn vừa tạo
9. Chon migration file cấu hình tunnel
10. Tạo 1 public hostname đến máy tính và port của bạn với giao thức tcp
11. Trên máy tính host, dừng và chạy lại lệnh run tunnel

## Cài đặt phần mềm phục vụ Monitoring bằng docker compose

### Câu thư mục

monitoring/

├── docker-compose.yml

├── grafana/

│ ── grafana-datasources.yml

├── opentelemetry/

│ └── otel-collector.yml

├── prometheus/

│ └── prometheus.yml

├── promtail/

│ └── promtail-config.yml

└── tempo/

│ └── tempo.yaml

### Viết file docker compose

```yml
version: '3.8'

services:
   prometheus:
      image: prom/prometheus:latest
      container_name: prometheus_container
      ports:
         - "9090:9090"
      volumes:
         - ./prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
         - ./prometheus/data:/prometheus
      restart: unless-stopped
      networks:
         - fashion_e_commerce_monitoring

   grafana:
      image: grafana/grafana:latest
      container_name: grafana_container
      ports:
         - "3000:3000"
      volumes:
         - ./grafana/data:/var/lib/grafana
         - ./grafana/grafana-datasources.yml:/etc/grafana/provisioning/datasources/datasources.yml
      environment:
         - GF_SECURITY_ADMIN_USER=admin
         - GF_SECURITY_ADMIN_PASSWORD=admin
      depends_on:
         - prometheus
         - loki
         - tempo
      restart: unless-stopped
      networks:
         - fashion_e_commerce_monitoring

   loki:
      image: grafana/loki:latest
      container_name: loki_container
      ports:
         - "3100:3100"
      command: -config.file=/etc/loki/local-config.yaml
      restart: unless-stopped
      networks:
         - fashion_e_commerce_monitoring

   otel-collector:
      image: otel/opentelemetry-collector-contrib:0.82.0
      container_name: otel_container
      restart: always
      command:
         - --config=/etc/otelcol-contrib/otel-collector.yml
      volumes:
         - ./opentelemetry/otel-collector.yml:/etc/otelcol-contrib/otel-collector.yml
      ports:
         - "1888:1888" # pprof extension
         - "8888:8888" # Prometheus metrics exposed by the collector
         - "8889:8889" # Prometheus exporter metrics
         - "13133:13133" # health_check extension
         - "4318:4318" # OTLP http receiver
         - "4317:4317" # OTLP grpc receiver
         - "55679:55679" # zpages extension
      depends_on:
         - tempo
      networks:
         - fashion_e_commerce_monitoring

   tempo:
      image: grafana/tempo:latest
      container_name: tempo_container
      volumes:
         - ./tempo/tempo.yaml:/etc/tempo.yaml
      command: [ "-config.file=/etc/tempo.yaml" ]
      ports:
         - "3200:3200" # tempo
         - "9095:9095" # tempo
         - "4317" # otlp grpc
         - "4318" # otlp grpc
      networks:
         - fashion_e_commerce_monitoring
      depends_on:
         - minio

   minio:
      image: quay.io/minio/minio:latest
      container_name: minio_container
      environment:
         - MINIO_ROOT_USER=admin
         - MINIO_ROOT_PASSWORD=LamNguyen@1305
      command: server /home/shared --console-address ":9999"
      ports:
         - 9990:9000
         - 9999:9999
      restart: unless-stopped
      volumes:
         - ./minio/data:/home/shared
      networks:
         - fashion_e_commerce_monitoring
      healthcheck:
         test: [ "CMD", "curl", "-f", "http://localhost:9990/minio/health/live" ]
         interval: 1m30s
         timeout: 30s
         retries: 5

networks:
   fashion_e_commerce_monitoring:
      driver: bridge
```

### Cấu hình file `grafana/grafana-datasources.yml`

```yml
apiVersion: 1

datasources:
   - name: Prometheus
     type: prometheus
     uid: prometheus
     access: proxy
     orgId: 1
     url: http://prometheus:9090
     basicAuth: false
     isDefault: false
     version: 1
     editable: false
     jsonData:
        httpMethod: GET

   - name: Loki
     type: loki
     uid: loki
     access: proxy
     orgId: 1
     url: http://loki:3100
     basicAuth: false
     isDefault: false
     version: 1
     editable: false

   - name: Tempo
     type: tempo
     uid: tempo
     access: proxy
     orgId: 1
     url: http://tempo:3200
     basicAuth: false
     isDefault: true
     version: 1
     editable: false
     apiVersion: 1
     jsonData:
        httpMethod: GET
        tracesToLogsV2:
           datasourceUid: loki
           spanStartTimeShift: '-1h'
           spanEndTimeShift: '1h'
           filterByTraceID: true
           filterBySpanID: true
           tags:
              - key: 'service.name'
                value: 'job'
        tracesToMetrics:
           datasourceUid: 'prometheus'
           spanStartTimeShift: '-1h'
           spanEndTimeShift: '1h'
           tags:
              - key: 'service.name'
                value: 'job'
        serviceMap:
           datasourceUid: 'prometheus'
        nodeGraph:
           enabled: true
        search:
           hide: false
        traceQuery:
           timeShiftEnabled: true
           spanStartTimeShift: '-1h'
           spanEndTimeShift: '1h'
        spanBar:
           type: 'Tag'
           tag: 'http.path'
        streamingEnabled:
           search: false
           metrics: false
```

### Cấu hình file `prometheus/prometheus.yml`

```yml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: [ 'prometheus:9090' ]
        labels:
          application: Prometheus Service

  - job_name: 'loki'
    static_configs:
      - targets: [ 'loki:3100' ]
        labels:
          application: Loki Service

  - job_name: 'config_server_service'
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:1306' ]
        labels:
          application: Config Server Service

  - job_name: 'api_gateway_service'
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8888' ]
        labels:
          application: Api Gateway Service

  - job_name: 'eureka_server_service'
    metrics_path: /fashion-e-commerce-eureka-server/actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8761' ]
        labels:
          application: Eureka Server Service

  - job_name: 'notification_service'
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8000' ]
        labels:
          application: Notification Service

  - job_name: 'authentication_service'
    metrics_path: /api/actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8001' ]
        labels:
          application: Authentication Service

  - job_name: 'profile_service'
    metrics_path: /api/actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8002' ]
        labels:
          application: Profile Service

  - job_name: 'product_service'
    metrics_path: /api/actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8003' ]
        labels:
          application: Product Service

  - job_name: 'media_service'
    metrics_path: /api/actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8004' ]
        labels:
          application: Media Service

  - job_name: 'inventory_service'
    metrics_path: /api/actuator/prometheus
    static_configs:
      - targets: [ 'host.docker.internal:8005' ]
        labels:
          application: Inventory Service

```


### Cấu hình file `tempo/tempo.yml`

```yml
auth_enabled: false

server:
  http_listen_port: 3200
  grpc_listen_port: 9095

distributor:
  receivers:
    otlp:
      protocols:
        grpc:
          endpoint: 0.0.0.0:4317   # ✅ QUAN TRỌNG
        http:
          endpoint: 0.0.0.0:4318   # ✅ Nên thêm nếu dùng HTTP

ingester:
  trace_idle_period: 10s
  max_block_duration: 5m

compactor:
  compaction:
    compaction_window: 1h

storage:
  trace:
    backend: s3
    s3:
      endpoint: minio:9000
      insecure: true
      bucket: tempo-trace
      access_key: Tempo
      secret_key: TempoTest
      region: us-east-1

metrics_generator:
  registry:
    external_labels:
      source: tempo
```

## Cấu hình OpenTelemetry

### 🔍 OpenTelemetry là gì?

**OpenTelemetry (OTel)** là một bộ công cụ **mã nguồn mở** được thiết kế để thu thập dữ liệu **telemetry** (logs,
metrics, traces) từ hệ thống phần mềm nhằm phục vụ cho việc **quan sát hệ thống** (observability).

> OpenTelemetry hỗ trợ nhiều ngôn ngữ (Java, Go, Python, v.v) và có thể thu thập dữ liệu từ các ứng dụng, sau đó gửi đến
> các backend như Grafana, Jaeger, Tempo, Prometheus, Elasticsearch, v.v.

---

### 🚀 Mục tiêu

Hướng dẫn chạy ứng dụng Java với **OpenTelemetry Java Agent** để tự động thu thập **trace, log, metric** và gửi về *
*OpenTelemetry Collector** qua giao thức OTLP (gRPC hoặc HTTP).

---

### 🛠️ Cài đặt OpenTelemetry Java Agent

1. **Tải OpenTelemetry Java Agent**

```bash
curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar -o opentelemetry-javaagent.jar
```

2. **Cấu hình các thông số của OpenTelemetry**

```yml
receivers:
   otlp:
      protocols:
         grpc:
         http:

exporters:
   otlp:
      endpoint: tempo:4317
      tls:
         insecure: true
   logging:
      loglevel: debug
   loki:
      endpoint: http://loki:3100/loki/api/v1/push

service:
   pipelines:
      traces:
         receivers: [ otlp ]
         exporters: [ otlp ]
      metrics:
         receivers: [ otlp ]
         exporters: [ logging ]
      logs:
         receivers: [ otlp ]
         exporters: [ loki, logging ]
```

3. **Chạy OpenTelemetry Java Agent**

[Link tìm hiểu các cấu hình các instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation)

- Trên Intellij

Cấu hình thêm VM options (Add VM options) trong phần cấu hình chạy dự án

```VM options
-javaagent:path\opentelemetry-javaagent.jar
-Dotel.exporter.otlp.protocol=grpc
-Dotel.exporter.otlp.endpoint=http://localhost:4317
-Dotel.javaagent.debug=true //Bật chế độ debug
```

---Bắt đầu: Cấu hình chạy kèm với logs---

[Link tìm hiểu các thông số logback](https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/instrumentation/logback/logback-appender-1.0/javaagent/README.md)

```VM options
-javaagent:path\opentelemetry-javaagent.jar
-Dotel.exporter.otlp.protocol=grpc || http/protobuf
-Dotel.exporter.otlp.endpoint=http://localhost:4317
-Dotel.javaagent.debug=true //Bật chế độ debug
-Dotel.logs.exporter=otlp
-Dotel.instrumentation.logback-appender.enabled=true
-Dotel.instrumentation.logback-appender.experimental-log-attributes=true
-Dotel.instrumentation.logback-appender.experimental.capture-code-attributes=true
-Dotel.instrumentation.logback-appender.experimental.capture-marker-attribute=true
-Dotel.instrumentation.logback-appender.experimental.capture-key-value-pair-attributes=true
-Dotel.instrumentation.logback-appender.experimental.capture-logger-context-attributes=true
-Dotel.instrumentation.logback-appender.experimental.capture-mdc-attributes=*
```

---Kết thức: Cấu hình chạy kèm với logs---

- Bằng Terminal

```VM options
java -javaagent:path\opentelemetry-javaagent.jar \
-Dotel.exporter.otlp.protocol=grpc \
-Dotel.exporter.otlp.endpoint=http://localhost:4317 \
-Dotel.javaagent.debug=true \ //Bật chế độ debug
-jar target/your-application.jar
```

---Bắt đầu: Cấu hình chạy kèm với logs---

[Link tìm hiểu các thông số logback](https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/instrumentation/logback/logback-appender-1.0/javaagent/README.md)

```VM options
java -javaagent:path\opentelemetry-javaagent.jar \
-Dotel.exporter.otlp.protocol=grpc \
-Dotel.exporter.otlp.endpoint=http://localhost:4317 \
-Dotel.javaagent.debug=true \ //Bật chế độ debug
-Dotel.logs.exporter=otlp \
-Dotel.instrumentation.logback-appender.enabled=true \
-Dotel.instrumentation.logback-appender.experimental-log-attributes=true \
-Dotel.instrumentation.logback-appender.experimental.capture-code-attributes=true \
-Dotel.instrumentation.logback-appender.experimental.capture-marker-attribute=true \
-Dotel.instrumentation.logback-appender.experimental.capture-key-value-pair-attributes=true \
-Dotel.instrumentation.logback-appender.experimental.capture-logger-context-attributes=true \
-Dotel.instrumentation.logback-appender.experimental.capture-mdc-attributes=* \
-jar target/your-application.jar
```

---Kết thức: Cấu hình chạy kèm với logs---