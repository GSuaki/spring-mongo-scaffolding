package com.inomind.modelo.springmongo.utils;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class JPAUtils {

	public static Order getOrder(String propriedade){
		return new Sort.Order(Sort.Direction.ASC, propriedade).ignoreCase();
	}
	
}
