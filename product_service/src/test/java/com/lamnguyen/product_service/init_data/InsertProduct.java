/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:31 PM-08/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.init_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.product_service.domain.ApiResponseError;
import com.lamnguyen.product_service.domain.ApiResponseSuccess;
import com.lamnguyen.product_service.domain.request.DataProductRequest;
import com.lamnguyen.product_service.domain.unformat.DataProductUnformat;
import com.nimbusds.jose.shaded.gson.TypeAdapter;
import com.nimbusds.jose.shaded.gson.stream.JsonReader;
import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class InsertProduct {
	String folderData = "D:\\tai_lieu_hoc_tap\\chuyen_de_web\\fashion_e_commerce\\server\\product_service\\data";
	String folderResource = "D:\\tai_lieu_hoc_tap\\chuyen_de_web\\fashion_e_commerce\\resources_train\\coolmate_imges";
	ObjectMapper objectMapper = new ObjectMapper();
	OkHttpClient client = new OkHttpClient.Builder()
			.connectTimeout(2, TimeUnit.MINUTES)      // timeout khi thiết lập kết nối TCP
			.writeTimeout(3, TimeUnit.MINUTES)        // timeout khi gửi dữ liệu lên server
			.readTimeout(4, TimeUnit.MINUTES)         // timeout khi đọc dữ liệu từ server
			.build();
	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2tpbWktZmFzaGlvbi1lLWNvbW1lcmNlLmNvbSIsInN1YiI6ImtpbWlub25hd2ExMzA1QGdtYWlsLmNvbSIsImV4cCI6MTg3MTAzNzcxNCwicGF5bG9hZCI6eyJyZWZyZXNoVG9rZW5JZCI6IjE0Yjc5MzE2LTlkZGQtNDRkZS1hMjI1LTlkOTExNTk4MDE2NyIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwidXNlcklkIjoxLCJ0eXBlIjoiQUNDRVNTX1RPS0VOIiwiZW1haWwiOiJraW1pbm9uYXdhMTMwNUBnbWFpbC5jb20ifSwiaWF0IjoxNzQ2NjIxNzE0LCJqdGkiOiJkZGM5YTJkMC0zOWQ1LTQ4N2QtYTViMC01MWI4YWE1NjQ3NmQifQ.0Dsvpuo5aY28O4aev3irASGM2xvpoxjWaaVJbUwRDis";

	@Test
	public void uploadProduct() throws IOException, InterruptedException {
		var collectionString = "quan-short-nu";
		var collectionId = "681ca3252a59db3110ec0b9e";
		var fileData = "collection_quan-short-nu.json";
		boolean testing = false;
		File file = new File(folderData + File.separator + "unformat" + File.separator + fileData); // Đường dẫn tới file JSON
		var folderCollection = folderResource + File.separator + collectionString;
		uploadProduct(collectionId, collectionString, folderResource, folderCollection, file, testing);
		if (!testing) {
			Files.move(file.toPath(), new File(folderData + File.separator + "unformat" + File.separator + "done" + File.separator + fileData).toPath());
		}
	}

	@Test
	public void reUploadProduct() throws IOException, InterruptedException {
		var collectionName = "ao-polo-nam";
		reUploadProduct(collectionName);
	}

	private void move(File source, String target) throws IOException {
		var buffer = new byte[1024];
		var inputStream = new FileInputStream(source);
		var outputStream = new FileOutputStream(target);
		var length = 0;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
		source.delete();
	}


	private void uploadProduct(String collectionId, String collectionName, String folderResource, String folderCollection, File file, boolean testing) throws IOException, InterruptedException {
		var allProductFolder = new ArrayList<String>(Arrays.stream(new File(folderCollection).listFiles()).filter(File::isDirectory).map(File::getName).toList());
		var fileOut = new File(folderData + File.separator + "format\\" + collectionName + ".json");
		var dataOuts = new ArrayList<DataProductRequest>();
		var products = objectMapper.readValue(file, DataProductUnformat[].class);
		System.out.println("Total product will insert: " + products.length + " products");
		var inserted = new ArrayList<String>();
		for (var product : products) {
			var seoAlias = toSeoAlias(product.getTitle());
			if (inserted.contains(seoAlias)) continue;
			allProductFolder.remove(product.getSeoAlias());
			var dataOut = formatDataProductRequest(product, folderCollection, collectionId, testing);
			if (!testing)
				uploadProduct(dataOut, collectionName);
			dataOuts.add(dataOut);
			inserted.add(seoAlias);
		}

		System.out.println("========================================Result================================");

		System.out.println("All folder not used: " + allProductFolder.size());
		System.out.println(allProductFolder.toString());

		if (!allProductFolder.isEmpty()) throw new RuntimeException("Has data unformatted");
		objectMapper.writerWithDefaultPrettyPrinter()
				.writeValue(fileOut, dataOuts);
	}

	private DataProductRequest formatDataProductRequest(DataProductUnformat product, String folderCollection, String collectionId, boolean tesing) throws IOException, InterruptedException {
		var folderProduct = folderCollection + File.separator + product.getSeoAlias();
		var folderImageProduct = new File(folderProduct + File.separator + "images");

		var dataOut = objectMapper.convertValue(product, DataProductRequest.class);

		if (dataOut.getTags() == null) dataOut.setTags(new ArrayList<>());
		dataOut.setCollection(collectionId);

		System.out.println("==========================================Image product: " + product.getSeoAlias() + "==============================================");

		if (dataOut.getOptions() != null) {
			dataOut.getOptions().forEach(option -> {
				if (option.getType() == null) throw new RuntimeException("Has option without type");
			});
		}

		if (folderImageProduct.exists()) {
			System.out.println("Total image: " + folderImageProduct.listFiles().length);
			if (!tesing)
				for (var image : folderImageProduct.listFiles()) {
					if (dataOut.getImages() == null) {
						dataOut.setImages(new ArrayList<>());
					}
					dataOut.getImages().add(uploadFile(image));
				}
		}

		var options = dataOut.getOptions();
		dataOut.getOptionsValues().forEach(optionValue -> {
			var newValues = new ArrayList<String>();
			optionValue.options().forEach(option -> {
				newValues.add(option.getTitle());
				var folderImageOption = new File(folderProduct + File.separator + toSeoAlias(option.getTitle()) + File.separator + "images");
				if (folderImageOption.exists()) {
					System.out.println("Total image option " + toSeoAlias(option.getTitle()) + ": " + folderImageOption.listFiles().length);
					if (option.getImages() == null) option.setImages(new ArrayList<>());
					if (!tesing)
						for (var image : folderImageProduct.listFiles()) {
							try {
								option.getImages().add(uploadFile(image));
							} catch (IOException | InterruptedException e) {
								throw new RuntimeException(e);
							}
						}
				} else {
					System.out.println("Has option value but not image; Not has folder: " + toSeoAlias(option.getTitle()));
				}
			});
			var oldOption = options.stream().filter(o -> o.getType().equals(optionValue.type())).findFirst().orElse(null);
			oldOption.setValues(newValues);
		});
		return dataOut;
	}

	private String toSeoAlias(String title) {
		// Bước 1: Chuyển toàn bộ thành chữ thường
		String lowerCase = title.toLowerCase();

		// Bước 2: Chuẩn hóa Unicode để tách dấu ra khỏi ký tự
		String normalized = Normalizer.normalize(lowerCase, Normalizer.Form.NFD);

		// Bước 3: Xóa các dấu (ký tự thuộc nhóm dấu thanh và mũ)
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		String withoutDiacritics = pattern.matcher(normalized).replaceAll("");

		// Bước 4: Thay thế các ký tự đặc biệt riêng của tiếng Việt
		withoutDiacritics = withoutDiacritics.replaceAll("đ", "d");

		// Bước 5: Thay khoảng trắng bằng dấu gạch ngang
		return withoutDiacritics.replaceAll("\\s+", "-");
	}

	public void testUploadFile() throws IOException, InterruptedException {
		var file = new File("D:\\tai_lieu_hoc_tap\\chuyen_de_web\\fashion_e_commerce\\server\\product_service\\data\\unformat\\1.png");
		var response = uploadFile(file);
		System.out.println(response);
	}

	public String uploadFile(File file) throws IOException, InterruptedException {
		String url = "http://localhost:8004/api/media/v1";

		// Tạo body cho file upload (multipart)
		RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/jpeg"));
		MultipartBody requestBody = new MultipartBody.Builder()
				.addFormDataPart("file", file.getName(), fileBody)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.addHeader("Authorization", "Bearer " + token)
				.post(requestBody)
				.build();

		// Gửi request
		try (Response response = client.newCall(request).execute()) {
			System.out.println("Status Code: " + response.code());
			if (response.code() < 300) {
				if (response.body() != null) {
					var body = objectMapper.readValue(response.body().byteStream(), ApiResponseSuccess.class);
					System.out.println("Response Body: " + body.getData().toString());
					return body.getData().toString();
				}
			} else if (response.body() != null) {
				var body = objectMapper.readValue(response.body().byteStream(), ApiResponseError.class);
				System.out.println("Response Body: " + body.getError().toString());
			}
		}

		throw new RuntimeException("Upload file error");
	}

	public void uploadProduct(DataProductRequest data, String collectionName, boolean reupload) throws IOException, InterruptedException {
		String url = "http://localhost:8003/api/product/admin/v1";

		mkdir(folderData + File.separator + "saved" + File.separator + collectionName);
		mkdir(folderData + File.separator + "unsaved" + File.separator + collectionName);

		var fileData = new File(folderData + File.separator + (reupload ? "un" : "") + "saved" + File.separator + collectionName + File.separator +
				(data.getTitle().replaceAll("[\\/]", " phần ")).replaceAll("\"", "'")
				+ ".json");
		objectMapper.writerWithDefaultPrettyPrinter()
				.writeValue(fileData, data);

		// Tạo request body với content-type JSON
		RequestBody requestBody = RequestBody.create(fileData, MediaType.parse("application/json"));

		// Tạo HTTP POST request
		Request request = new Request.Builder()
				.url(url) // <-- Đổi URL API thật của bạn
				.addHeader("Authorization", "Bearer " + token)
				.addHeader("Content-Type", "application/json")
				.post(requestBody)
				.build();

		// Gửi và nhận phản hồi
		try (Response response = client.newCall(request).execute()) {
			System.out.println("Status code: " + response.code());

			if (response.code() >= 200 && response.code() < 300) {
				new File(folderData + File.separator + "unsaved" + File.separator + collectionName + File.separator + data.getTitle() + ".json").delete();
				if (reupload) {
					Files.move(fileData.toPath(), new File(folderData + File.separator + "saved" + File.separator + collectionName + File.separator + data.getTitle() + ".json").toPath());
				}
			} else if (response.code() >= 400) {
				if (response.body() != null) {
					System.out.println("Response error: " + new String(response.body().byteStream().readAllBytes()));
				}
				fileData.delete();
				fileData = new File(folderData + File.separator + "unsaved" + File.separator + collectionName + File.separator + data.getTitle() + ".json");
				objectMapper.writerWithDefaultPrettyPrinter()
						.writeValue(fileData, data);
			}
		}
	}

	public void uploadProduct(DataProductRequest data, String collectionName) throws IOException, InterruptedException {
		uploadProduct(data, collectionName, false);
	}

	public void reUploadProduct(String collectionName) throws IOException, InterruptedException {
		var folderCollection = new File(folderData + File.separator + "unsaved" + File.separator + collectionName);

		for (var file : folderCollection.listFiles()) {
			var data = objectMapper.readValue(file, DataProductRequest.class);
			uploadProduct(data, collectionName, true);
		}
	}

	class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

		private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		@Override
		public void write(JsonWriter out, LocalDateTime value) throws IOException {
			if (value == null) {
				out.nullValue();
			} else {
				out.value(value.format(formatter));
			}
		}

		@Override
		public LocalDateTime read(JsonReader in) throws IOException {
			String value = in.nextString();
			return LocalDateTime.parse(value, formatter);
		}
	}

	private void mkdir(String path) {
		var file = new File(path);
		if (!file.exists()) file.mkdirs();
	}
}




