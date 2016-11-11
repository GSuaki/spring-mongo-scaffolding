package com.inomind.modelo.springmongo.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Utils {

	public static String formatDouble(Double valor){
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(new Locale("pt", "BR"));
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.applyPattern("#,##0.00");
		return decimalFormat.format(valor);
	}
	
	public static Double getDouble(String valor) throws ParseException{
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(new Locale("pt", "BR"));
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.applyPattern("#,##0.00");
		return decimalFormat.parse(valor).doubleValue();
	}
	
	public static String formatInteger(Integer valor){
		NumberFormat format = DecimalFormat.getInstance(new Locale("pt", "BR"));
		format.setRoundingMode(RoundingMode.FLOOR);
		format.setMinimumFractionDigits(0);
		format.setMaximumFractionDigits(2);
		return format.format(valor);
	}
	
}
