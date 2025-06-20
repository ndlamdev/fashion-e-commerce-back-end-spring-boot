/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:56 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.notification_service.utils.enums;

import lombok.Getter;

@Getter
public enum BrevoTemplate {
	VERITY_ACCOUNT(2), RESET_PASSWORD(3), CHANGE_PASSWORD(4);

	private long id;

	BrevoTemplate(long id) {
		this.id = id;
	}
}