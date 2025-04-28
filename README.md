# Dự án Thương mại Điện tử Bán Phần Áo (Microservices)

## Mô tả

**Khám phá thế giới áo thun đa dạng và phong cách tại Fashion E-Commerce!** Dự án thương mại điện tử này được xây dựng bằng kiến trúc microservices tiên tiến, đảm bảo hiệu suất vượt trội, khả năng mở rộng linh hoạt và trải nghiệm người dùng tuyệt vời. Từ những thiết kế basic đến những họa tiết cá tính, Fashion E-Commerce là điểm đến lý tưởng để bạn tìm kiếm chiếc áo hoàn hảo, thể hiện chất riêng của mình.

## Kiến trúc Microservices

Ứng dụng được xây dựng dựa trên kiến trúc microservices, bao gồm các dịch vụ chính sau:

* **Eureka Server:** Dịch vụ quản lý đăng ký và khám phá dịch vụ.
* **API Gateway:** Điểm truy cập duy nhất cho tất cả các yêu cầu từ client, định tuyến yêu cầu đến các dịch vụ backend phù hợp.
* **Notification Service:** Dịch vụ chịu trách nhiệm gửi thông báo đến người dùng (ví dụ: email, SMS).
* **Authentication Service:** Dịch vụ quản lý việc xác thực và ủy quyền người dùng.
* **(Các dịch vụ khác - bạn có thể thêm vào đây nếu có)**

## Thứ tự chạy các Service

Để đảm bảo các dịch vụ hoạt động đúng cách, bạn cần khởi động chúng theo thứ tự sau:

1.  **Khởi động Docker và Kafka:**
    Chạy lệnh sau trong thư mục chứa file `docker-compose.yml`:
    ```bash
    docker compose up -d
    ```
    Lệnh này sẽ khởi động các container Docker, bao gồm cả Kafka (nếu bạn sử dụng Kafka cho giao tiếp giữa các dịch vụ).

2.  **Chạy Service Eureka Server:**
    Khởi động ứng dụng Eureka Server. Đảm bảo rằng Eureka Server đã khởi động thành công trước khi chạy các dịch vụ khác.

3.  **Chạy Service API Gateway:**
    Khởi động ứng dụng API Gateway. API Gateway sẽ đăng ký với Eureka Server để khám phá các dịch vụ khác.

4.  **Chạy Service Notification:**
    Khởi động ứng dụng Notification Service. Dịch vụ này sẽ có thể đăng ký với Eureka Server và sẵn sàng nhận các yêu cầu gửi thông báo.

5.  **Chạy Service Authentication:**
    Khởi động ứng dụng Authentication Service. Dịch vụ này sẽ chịu trách nhiệm xác thực người dùng và cũng sẽ đăng ký với Eureka Server.

6.  **(Chạy các service khác):**
    Tiếp tục khởi động các dịch vụ còn lại của bạn theo thứ tự phụ thuộc (nếu có). Ví dụ: các service liên quan đến quản lý sản phẩm, giỏ hàng, thanh toán,...

## Drive File Config Dev

Bạn có thể tìm thấy các file cấu hình cho môi trường phát triển (dev) tại đường dẫn sau:

[fashion-e-commerce - Google Drive](https://drive.google.com/drive/u/0/folders/1cvq1WAdgddmPXBW4BXm1tLvYZPBLIYGn)


## Công nghệ sử dụng

* **Ngôn ngữ lập trình:** Java, Python
* **Framework:** Spring Boot, Django
* **Thư viện sử dụng:**
    * Spring Boot JPA
    * Spring Mongo
    * Spring Eureka
    * Spring Security
    * Spring Kafka
    * Brevo Send Mail
    * ...

## Yêu cầu hệ thống

* **Bộ nhớ:** 128GB
* **RAM:** 16GB
* **Bộ xử lý:** Intel thế hệ thứ 10 trở lên hoặc các dòng chip khác tương đương

## Hướng dẫn cài đặt

1.  Clone dự án từ Github: [ndlamdev/fashion-e-commerce-back-end-sping-boot](https://github.com/ndlamdev/fashion-e-commerce-back-end-sping-boot)
2.  Chạy theo hướng dẫn chi tiết trong repository đã clone.

## Đóng góp

Mọi đóng góp đều được hoan nghênh. Vui lòng liên hệ qua email: [ndlam.dev@gmail.com](mailto:ndlam.dev@gmail.com) để thảo luận về các ý tưởng đóng góp.

## Liên hệ

Email: [ndlam.dev@gmail.com](mailto:ndlam.dev@gmail.com)