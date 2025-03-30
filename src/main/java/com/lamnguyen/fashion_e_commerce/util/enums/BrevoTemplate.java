/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:56 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.util.enums;

import lombok.Getter;

@Getter
public enum BrevoTemplate {
    VERITY(2), RESET_PASSWORD(3);

    private long id;

    BrevoTemplate(long id) {
        this.id = id;
    }
}
