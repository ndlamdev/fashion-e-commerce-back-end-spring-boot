/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:50 AM-15/06/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.grpc;

import com.lamnguyen.order_service.protos.GeneralInfo;

import java.util.List;
import java.util.Map;

public interface IOrderServiceGrpcClient {
	Map<Long, GeneralInfo> getGeneralInfos(List<Long> ids);
}
