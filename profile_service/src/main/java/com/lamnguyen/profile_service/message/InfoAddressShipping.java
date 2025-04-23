package com.lamnguyen.profile_service.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoAddressShipping {
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
