/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:37 AM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.utils.format.folder;

import java.io.*;

public class FormatFolder {
	public static void format(String path){
		File dir = new File(path);

		for (File category : dir.listFiles()) {
			if (category.isFile()) {
				category.delete();
				continue;
			}

			for (var product : category.listFiles()) {
				if (product.isFile()) {
					product.delete();
					continue;
				}
				for (var option : product.listFiles()) {
					if (option.isFile()) {
						option.delete();
						continue;
					}

					if (option.getName().equalsIgnoreCase("thumbnail")) {
						for (var th : option.listFiles()) {
							th.delete();
						}
						option.delete();
						continue;
					}

					if (option.getName().equalsIgnoreCase("images")) {
						move(option, product);
						continue;
					}

					var images = option.listFiles()[0];
					move(images, product);
					option.delete();
				}
			}
		}
	}

	private static void move(File option, File product) {
		for (var image : option.listFiles()) {
			try {
				moveHelper(image, product);
			} catch (IOException e) {
				System.out.println("Lỗi tại sản phẩm: " + product.getName() + " -> option: " + option.getPath());
			}
		}
		option.delete();
	}

	private static void moveHelper(File image, File product) throws IOException {
		var fileOut = new File(product.toPath() + File.separator + image.getName());
		if (fileOut.exists()) {
			image.delete();
			return;
		}

		var buffer = new byte[1024000];
		var readed = 0;
		var input = new BufferedInputStream(new FileInputStream(image));

		var output = new BufferedOutputStream(new FileOutputStream(fileOut));
		while ((readed = input.read(buffer)) != -1) output.write(buffer, 0, readed);

		input.close();
		output.close();
		image.delete();
	}
}
