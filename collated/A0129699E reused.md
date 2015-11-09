# A0129699E reused
###### storage\Converters.java
``` java

package storage;

/*
 * GSON Joda Time Serialisers
 *
 * Copyright 2013-2014 Greg Kopff
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.lang.reflect.Type;

/**
 * The {@code Converters} class contains static methods for registering Joda Time converters.
 */
@SuppressWarnings("deprecation")
public class Converters
{
	/** The specific genericized type for {@code DateMidnight}. */
	public static final Type DATE_MIDNIGHT_TYPE = new TypeToken<DateMidnight>(){}.getType();

	/** The specific genericized type for {@code DateTime}. */
	public static final Type DATE_TIME_TYPE = new TypeToken<DateTime>(){}.getType();

	/** The specific genericized type for {@code LocalDate}. */
	public static final Type LOCAL_DATE_TYPE = new TypeToken<LocalDate>(){}.getType();

	/** The specific genericized type for {@code LocalDateTime}. */
	public static final Type LOCAL_DATE_TIME_TYPE = new TypeToken<LocalDateTime>(){}.getType();

	/** The specific genericized type for {@code LocalTime}. */
	public static final Type LOCAL_TIME_TYPE = new TypeToken<LocalTime>(){}.getType();

	/** The specific genericized type for {@code Interval}. */
	public static final Type INTERVAL_TYPE = new TypeToken<Interval>(){}.getType();

	/** The specific genericized type for {@code Duration}. */
	public static final Type DURATION_TYPE = new TypeToken<Duration>(){}.getType();

	/**
	 * Registers all the Joda Time converters.
	 * @param builder The GSON builder to register the converters with.
	 * @return A reference to {@code builder}.
	 */
	public static GsonBuilder registerAll(GsonBuilder builder)
	{
		if (builder == null) { throw new NullPointerException("builder cannot be null"); }

		//    registerDateMidnight(builder);
		registerDateTime(builder);
		//    registerDuration(builder);
		//    registerLocalDate(builder);
		//    registerLocalDateTime(builder);
		//    registerLocalTime(builder);
		//    registerInterval(builder);

		return builder;
	}

	/**
	 * Registers the {@link DateMidnight} converter.
	 * @param builder The GSON builder to register the converter with.
	 * @return A reference to {@code builder}.
	 */
	public static GsonBuilder registerDateMidnight(GsonBuilder builder)
	{
		if (builder == null) { throw new NullPointerException("builder cannot be null"); }

		//    builder.registerTypeAdapter(DATE_MIDNIGHT_TYPE, new DateMidnightConverter());

		return builder;
	}

	/**
	 * Registers the {@link DateTime} converter.
	 * @param builder The GSON builder to register the converter with.
	 * @return A reference to {@code builder}.
	 */
	public static GsonBuilder registerDateTime(GsonBuilder builder)
	{
		if (builder == null) { throw new NullPointerException("builder cannot be null"); }

		builder.registerTypeAdapter(DATE_TIME_TYPE, new DateTimeConverter());

		return builder;
	}


	/**
	 * Registers the {@link Duration} converter. + @param builder The GSON
	 * builder to register the converter with.
	 * 
	 * @return A reference to {@code builder}.
	 *
	 *         public static GsonBuilder registerDuration(GsonBuilder builder) {
	 *         if (builder == null) { throw new NullPointerException(
	 *         "builder cannot be null"); }
	 * 
	 *         builder.registerTypeAdapter(DURATION_TYPE, new
	 *         DurationConverter());
	 * 
	 *         return builder; }
	 * 
	 *         /** Registers the {@link LocalDate} converter.
	 * @param builder
	 *            The GSON builder to register the converter with.
	 * @return A reference to {@code builder}.
	 *
	 *         public static GsonBuilder registerLocalDate(GsonBuilder builder)
	 *         { if (builder == null) { throw new NullPointerException(
	 *         "builder cannot be null"); }
	 * 
	 *         builder.registerTypeAdapter(LOCAL_DATE_TYPE, new
	 *         LocalDateConverter());
	 * 
	 *         return builder; }
	 * 
	 *         /** Registers the {@link LocalDateTime} converter.
	 * @param builder
	 *            The GSON builder to register the converter with.
	 * @return A reference to {@code builder}.
	 *
	 *         public static GsonBuilder registerLocalDateTime(GsonBuilder
	 *         builder) { if (builder == null) { throw new NullPointerException(
	 *         "builder cannot be null"); }
	 * 
	 *         builder.registerTypeAdapter(LOCAL_DATE_TIME_TYPE, new
	 *         LocalDateTimeConverter());
	 * 
	 *         return builder; }
	 * 
	 *         /** Registers the {@link LocalTime} converter.
	 * @param builder
	 *            The GSON builder to register the converter with.
	 * @return A reference to {@code builder}.
	 *
	 *         public static GsonBuilder registerLocalTime(GsonBuilder builder)
	 *         { if (builder == null) { throw new NullPointerException(
	 *         "builder cannot be null"); }
	 * 
	 *         builder.registerTypeAdapter(LOCAL_TIME_TYPE, new
	 *         LocalTimeConverter());
	 * 
	 *         return builder; }
	 * 
	 *         /** Registers the {@link Interval} converter.
	 * @param builder
	 *            The GSON builder to register the converter with.
	 * @return A reference to {@code builder}.
	 *
	 *         public static GsonBuilder registerInterval(GsonBuilder builder) {
	 *         if (builder == null) { throw new NullPointerException(
	 *         "builder cannot be null"); }
	 * 
	 *         builder.registerTypeAdapter(INTERVAL_TYPE, new
	 *         IntervalConverter());
	 * 
	 *         return builder; }
	 */
}
```
###### storage\DateTimeConverter.java
``` java

/*
 * GSON Joda Time Serialisers
 *
 * Copyright 2013-2015 Greg Kopff
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package storage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * GSON serialiser/deserialiser for converting Joda {@link DateTime} objects.
 */

public class DateTimeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

	/**
	 * Gson invokes this call-back method during serialization when it
	 * encounters a field of the specified type.
	 * <p>
	 *
	 * In the implementation of this call-back method, you should consider
	 * invoking {@link JsonSerializationContext#serialize(Object, Type)} method
	 * to create JsonElements for any non-trivial field of the {@code src}
	 * object. However, you should never invoke it on the {@code src} object
	 * itself since that will cause an infinite loop (Gson will call your
	 * call-back method again).
	 * 
	 * @param src
	 *            the object that needs to be converted to Json.
	 * @param typeOfSrc
	 *            the actual type (fully genericized version) of the source
	 *            object.
	 * @return a JsonElement corresponding to the specified object.
	 */

	@Override
	public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
		final DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return new JsonPrimitive(fmt.print(src));
	}

	/**
	 * Gson invokes this call-back method during deserialization when it
	 * encounters a field of the specified type.<p>
	 *
	 * In the implementation of this call-back method, you should consider
	 * invoking
	 * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method
	 * to create objects for any non-trivial field of the returned object.
	 * However, you should never invoke it on the the same type passing
	 * {@code json} since that will cause an infinite loop (Gson will call your
	 * call-back method again).
	 * @param json The Json data being deserialized
	 * @param typeOfT The type of the Object to deserialize to
	 * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
	 * @throws JsonParseException if json is not in the expected format of {@code typeOfT}
	 */

	@Override
	public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// Do not try to deserialize null or empty values
		if (json.getAsString() == null || json.getAsString().isEmpty()) {
			return null;
		}

		final DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return fmt.parseDateTime(json.getAsString());
	}
}
```
