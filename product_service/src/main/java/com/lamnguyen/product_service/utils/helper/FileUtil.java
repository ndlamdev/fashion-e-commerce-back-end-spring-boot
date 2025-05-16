/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:46 PM-16/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.utils.helper;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public class FileUtil {
	public static File saveFile(MultipartFile file) throws IOException {
		var dir = new File("/tmp/product_service");
		if (!dir.exists()) dir.mkdir();
		var ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		var tempFile = new File(dir.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + ext.toLowerCase());
		var in = new BufferedInputStream(file.getInputStream());
		var out = new BufferedOutputStream(new FileOutputStream(tempFile));
		var buffer = new byte[1024 * 100];
		var readed = 0;
		while ((readed = in.read(buffer)) != -1) {
			out.write(buffer, 0, readed);
		}
		out.close();
		in.close();
		return tempFile;
	}
}
