/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:45 PM - 20/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.business.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;import com.fasterxml.jackson.databind.annotation.JsonNaming;import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "IFacebookGraphClient", url = "https://graph.facebook.com/v22.0/")
public interface IFacebookGraphClient {

	@GetMapping(value = "/debug_token?access_token={access_token}&input_token={access_token}&format=json&method=get&origin_graph_explorer=1&pretty=1&suppress_http_code=1&transport=cors", produces = "application/json")
	DebugTokenResponse debugToken(@RequestParam("access_token") String token);

	@GetMapping(value = "/oauth/access_token?grant_type=fb_exchange_token")
	ExchangeTokenResponse exchangeToken(@RequestParam("client_id") String appId, @RequestParam("client_secret") String appSecret, @RequestParam("fb_exchange_token") String token);

	@GetMapping(value = "/me?fields=id%2Cname%2Cemail%2Cfirst_name%2Clast_name%2Cpicture%2Clocale%2Ctimezone")
	ProfileUserFacebookResponse getProfile(@RequestParam("access_token") String accessToken);

	record DebugTokenResponse(
			DataDebugTokenResponse data
	) {
		@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
		public record DataDebugTokenResponse(
				String appId,
				String type,
				String application,
				long dataAccessExpiresAt,
				long expiresAt,
				@JsonProperty("is_valid")
				boolean valid,
				String userId,
				String[] scopes
		) {
		}
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	record ExchangeTokenResponse(
			String accessToken,
			String tokenType,
			long expiresIn
	) {
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	record ProfileUserFacebookResponse(
			String id,
			String name,
			String firstName,
			String lastName,
			@JsonProperty("picture")
			Picture avatar
	) {
		public record Picture(
				PictureData data
		) {
			@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
			public record PictureData(
					double height,
					double width,
					boolean isSilhouette,
					String url
			) {
			}
		}
	}
}
