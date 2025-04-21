
package com.lamnguyen.authentication_service.utils.enums;

public enum JwtType {
    ACCESS_TOKEN, RESET_PASSWORD, REFRESH_TOKEN, REGISTER_WITH_GOOGLE;

    public static JwtType getEnum(Object type) {
        if (type instanceof String data) {
            for (var e : JwtType.values()) {
                if (e.name().equals(data)) return e;
            }
        }
        return null;
    }
}