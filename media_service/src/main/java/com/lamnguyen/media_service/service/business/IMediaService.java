/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:21 AM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.business;

import com.lamnguyen.media_service.domain.dto.MediaDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMediaService {
	void upload(MultipartFile file);

	boolean existsById(String id);

	MediaDto getById(String id);

	List<MediaDto> getAllById(List<String> ids);
}
