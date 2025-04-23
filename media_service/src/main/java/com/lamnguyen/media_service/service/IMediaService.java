/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:21 AM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
	void upload(MultipartFile file);
}
