/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:54 PM-23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.domain.dto;

import com.lamnguyen.media_service.model.MongoBaseDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto extends MongoBaseDocument {
	String path;
	String displayName;
	String parentPath;
	String extend;
	String fileName;
}
