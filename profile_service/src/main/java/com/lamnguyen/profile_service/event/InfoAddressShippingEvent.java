package com.lamnguyen.profile_service.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoAddressShippingEvent {
    String fullName;
    String phone;
    String street;
    String ward;
    String wardCode;
    String district;
    String districtCode;
    String city;
    String cityCode;
    String country;
}
