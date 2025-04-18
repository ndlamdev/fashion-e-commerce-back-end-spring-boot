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