/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:29 AM - 19/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.grpc;

import com.lamnguyen.authentication_service.protos.UserResponse;

public interface IProfileCostumerGrpcClient {
	UserResponse findById(long id);
}
