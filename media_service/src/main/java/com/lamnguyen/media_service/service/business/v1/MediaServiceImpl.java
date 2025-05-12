/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:32 AM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.business.v1;

import com.lamnguyen.media_service.config.exception.ApplicationException;
import com.lamnguyen.media_service.config.exception.ExceptionEnum;
import com.lamnguyen.media_service.domain.dto.MediaDto;
import com.lamnguyen.media_service.mapper.IMediaMapper;
import com.lamnguyen.media_service.model.Media;
import com.lamnguyen.media_service.protos.MediaInfo;
import com.lamnguyen.media_service.repository.IMediaRepository;
import com.lamnguyen.media_service.service.business.IMediaService;
import com.lamnguyen.media_service.service.redis.IMediaRedisManage;
import com.lamnguyen.media_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaServiceImpl implements IMediaService {
	ApplicationProperty applicationProperty;
	IMediaRepository mediaRepository;
	IMediaRedisManage mediaManage;
	IMediaMapper mediaMapper;

	@Override
	public String upload(MultipartFile file) {
		if (file.getContentType() == null || !file.getContentType().startsWith("image"))
			throw ApplicationException.createException(ExceptionEnum.ERROR_FILE_TYPE);
		var result = uploadHelper(file).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.UPLOAD_FAILED));
		var fileResult = new File(result);
		return mediaRepository.save(Media.builder()
				.path(result.substring(result.lastIndexOf(File.separator) + 1))
				.extend(fileResult.getName().substring(fileResult.getName().lastIndexOf(".") + 1))
				.displayName(fileResult.getName().substring(0, fileResult.getName().lastIndexOf(".")))
				.parentPath(applicationProperty.getPathFileManager())
				.fileName(fileResult.getName())
				.build()).getId();
	}

	public Optional<String> uploadHelper(MultipartFile file) {
		var extend = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
		var fileName = applicationProperty.getPathFileManager() + File.separator + UUID.randomUUID() + extend;
		try (
				var is = new BufferedInputStream(file.getInputStream());
				var os = new BufferedOutputStream(new FileOutputStream(fileName))
		) {
			var buffed = new byte[1024000];
			var readed = 0;
			while ((readed = is.read(buffed)) != -1)
				os.write(buffed, 0, readed);

		} catch (IOException e) {
			return Optional.empty();
		}

		return Optional.of(fileName);
	}

	@Override
	public boolean existsById(String id) {
		try {
			getById(id);
			return true;
		} catch (ApplicationException ignored) {
			return false;
		}
	}

	@Override
	public MediaDto getById(String id) {
		return mediaManage.get(id)
				.or(() -> mediaManage.cache(id, () -> mediaRepository.findById(id).map(mediaMapper::toDto)))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
	}

	@Override
	public List<MediaDto> getAllById(List<String> ids) {
		var result = new ArrayList<MediaDto>(ids.size());
		var listTasks = new ArrayList<CompletableFuture>();
		for (var id : ids) {
			listTasks.add(CompletableFuture.runAsync(() -> {
				try {
					result.add(getById(id));
				} catch (ApplicationException ignored) {
				}
			}));

		}
		CompletableFuture.allOf(listTasks.toArray(new CompletableFuture[0])).join();
		return result;
	}

	@Override
	public Map<String, MediaInfo> getMediaByIds(List<String> ids) {
		var result = new HashMap<String, MediaInfo>(ids.size());
		var listTasks = new ArrayList<CompletableFuture>();
		for (var id : ids) {
			listTasks.add(CompletableFuture.runAsync(() -> {
				try {
					result.put(id, mediaMapper.toMediaInfo(getById(id)));
				} catch (ApplicationException ignored) {
				}
			}));
		}
		CompletableFuture.allOf(listTasks.toArray(new CompletableFuture[0])).join();
		return result;
	}
}
