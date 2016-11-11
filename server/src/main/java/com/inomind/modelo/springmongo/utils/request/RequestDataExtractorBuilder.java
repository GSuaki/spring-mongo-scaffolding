package com.inomind.modelo.springmongo.utils.request;

import java.util.ArrayList;
import java.util.List;

import com.inomind.modelo.springmongo.exception.BuilderException;

/**
 * @author GSuaki
 */
public class RequestDataExtractorBuilder {

	private List<RequestDataExtractor> extractors;

	public RequestDataExtractor build() {

		if (this.extractors == null) {
			throw new BuilderException(this.getClass(), "You must call at least one 'forXXX' method");
		}

		if (this.extractors.size() == 1) {
			return this.extractors.iterator().next();
		} else {
			return new GenericRequestDataExtractor(this.extractors);
		}
	}

	/**
	 * @return an instance working for headers, parameters and cookies.
	 */
	public RequestDataExtractorBuilder forAllPossibilities() {
		return this.forHeaders().forCookies();
	}

	public RequestDataExtractorBuilder forHeaders() {
		return this.forType(HeaderDataExtractor.class);
	}

	public RequestDataExtractorBuilder forCookies() {
		return this.forType(CookieDataExtractor.class);
	}

	private RequestDataExtractorBuilder forType(Class<? extends RequestDataExtractor> type) {
		if (this.extractors == null) {
			this.extractors = new ArrayList<>();
		}

		if (!this.contains(type)) {
			try {
				this.extractors.add(type.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return this;
	}

	private boolean contains(Class<? extends RequestDataExtractor> clazz) {

		return this.extractors.stream().anyMatch(p -> clazz.isAssignableFrom(p.getClass()));
	}
}