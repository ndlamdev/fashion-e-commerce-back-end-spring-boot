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