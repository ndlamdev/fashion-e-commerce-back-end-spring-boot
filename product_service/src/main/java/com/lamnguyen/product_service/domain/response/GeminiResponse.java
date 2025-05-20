package com.lamnguyen.product_service.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeminiResponse {
	private List<Candidate> candidates;
	private UsageMetadata usageMetadata;
	private String modelVersion;

	@Getter
	@Setter
	public static class Candidate {
		private Content content;
		private String finishReason;
		private double avgLogprobs;

		@Getter
		@Setter
		public static class Content {
			private List<Part> parts;
			private String role;

			@Getter
			@Setter
			public static class Part {
				private String text;
			}
		}
	}

	@Getter
	@Setter
	public static class UsageMetadata {
		private int promptTokenCount;
		private int candidatesTokenCount;
		private int totalTokenCount;
		private List<TokensDetails> promptTokensDetails;
		private List<TokensDetails> candidatesTokensDetails;

		@Getter
		@Setter
		public static class TokensDetails {
			private String modality;
			private int tokenCount;
		}
	}


}