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
    implementation 'net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE'
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
option java_package = "com.lamnguyen.authentication_service.protos";
option java_outer_classname = "HelloWorldProto";

// The greeting service definition.
service Simple {
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

\* Lưu ý: Nên clean trước khi build lại dự án

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