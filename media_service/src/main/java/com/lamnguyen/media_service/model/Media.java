/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:43 AM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "medias")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Media extends BaseEntity {
	String path;
	String displayName;
	String parentPath;
	String extend;
	String fileName;

	@PrePersist
	public void onCreate() {
		this.extend = this.fileName.substring(fileName.lastIndexOf(".") + 1);
		this.displayName = this.fileName.substring(0, fileName.lastIndexOf("."));
	}
}
