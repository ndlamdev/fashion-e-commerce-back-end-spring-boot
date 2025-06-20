/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:39 AM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.controller;

import com.lamnguyen.media_service.service.business.IMediaService;
import com.lamnguyen.media_service.utils.annotation.ApiMessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/media/v1")
public class MediaController {
	IMediaService mediaService;

	@PostMapping
	@ApiMessageResponse("Upload file success!")
	@PreAuthorize("hasAnyAuthority('UPLOAD_FILE','ROLE_ADMIN')")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		return mediaService.upload(file);
	}
}
