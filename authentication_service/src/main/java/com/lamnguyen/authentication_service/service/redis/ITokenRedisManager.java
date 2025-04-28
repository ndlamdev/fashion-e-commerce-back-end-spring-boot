/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:12 PM - 11/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.redis;

public interface ITokenRedisManager extends IRedisManager {
	void addTokenIdInBlackList(long userId, String tokenId);

	void setTokenId(long userId, String tokenId);

	void removeTokenId(long userId, String tokenId);

	boolean existTokenId(long userId, String tokenId);

	boolean existTokenIdInBlacklist(long userId, String tokenId);
}
