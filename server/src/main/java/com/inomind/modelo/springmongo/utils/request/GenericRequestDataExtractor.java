package com.inomind.modelo.springmongo.utils.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vinicius
 */
public class GenericRequestDataExtractor implements RequestDataExtractor {

	private List<RequestDataExtractor> innerExtractors;

	/**
	 * @param extractors
	 * @throws NullPointerException
	 * 		when the argument is null
	 * @throws IllegalArgumentException
	 * 		when the argument is empty
	 */
	public GenericRequestDataExtractor(List<RequestDataExtractor> extractors) throws NullPointerException, IllegalArgumentException {
		this.innerExtractors = extractors;
	}
	
	@Override
	public String extractData(HttpServletRequest request, String key) throws NullPointerException, IllegalArgumentException {

		for(RequestDataExtractor extractor : this.innerExtractors){
			String data = extractor.extractData(request, key);
			
			if(data != null){
				return data;
			}
		}
		
		return null;
	}
}