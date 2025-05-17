/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:43 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.business.v1;

import com.lamnguyen.order_service.service.business.IBaseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BaseServiceImpl implements IBaseService {
}
