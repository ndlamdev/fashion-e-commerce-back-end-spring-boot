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

<span style="color:red">* Chú ý: </span> Riêng với consumer cần cấu hình để cho consumer có thể trust các package mà
được gửi trong kafka và chấp nhận mapping. Có thể thay thành ``*`` nếu như đang trong quá trình dev.
