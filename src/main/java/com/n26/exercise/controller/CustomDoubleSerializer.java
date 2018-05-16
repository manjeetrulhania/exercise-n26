package com.n26.exercise.controller;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/*
 * serializer added to return 2 decimal points statistics 
 */
public class CustomDoubleSerializer extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
			final String pattern = "#.##";
			final DecimalFormat myFormatter = new DecimalFormat(pattern);
			final String output = myFormatter.format(value);
			gen.writeNumber(output);
	}
	
}
