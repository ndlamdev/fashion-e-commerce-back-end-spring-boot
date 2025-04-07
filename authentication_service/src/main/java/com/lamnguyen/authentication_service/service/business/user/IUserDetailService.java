/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:09 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.business.user;

import com.lamnguyen.authentication_service.event.SaveUserDetailEvent;

public interface IUserDetailService {
    void save(SaveUserDetailEvent user);
}
