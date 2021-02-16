package com.alex.common2.lang;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;;

public class Codecs {
  private static final Logger LOGGER = LoggerFactory.getLogger(Codecs.class);

  public static final ObjectMapper NO_NULL_FIELD_ORDERED_MAPPER = buildMapper().setSerializationInclusion(NON_NULL);

  public static Module javaTimeModule() {
    return new JavaTimeModule().addSerializer(LocalDate.class, new LocalDateSerializer(ISO_LOCAL_DATE))
        .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ISO_LOCAL_DATE_TIME));
  }

  public static ObjectMapper buildMapper() {
    return new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
        .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true).registerModule(javaTimeModule())
        .registerModule(new Jdk8Module()).registerModule(new ParameterNamesModule());
  }

  public static byte[] objToJsonBytes(Object object) {
    try {
      return NO_NULL_FIELD_ORDERED_MAPPER.writeValueAsBytes(object);
    } catch (JsonProcessingException e) {
      LOGGER.error(e.toString());
      throw new RuntimeException(e);
    }
  }

  public static <T> T jsonToObj(byte[] jsonBytes, Class<T> clazz) {
    try {
      return NO_NULL_FIELD_ORDERED_MAPPER.readValue(jsonBytes, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}