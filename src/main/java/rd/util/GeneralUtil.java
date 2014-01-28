package rd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GeneralUtil {

	public static String readFile(File f) throws IOException{
		return new String(Files.readAllBytes(Paths.get(f.toURI())));
	}
	
	public static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public static void logObject(Object o){
		System.out.println(new Gson().toJson(o));
	}
	
	/**
	 * TODO make sure everything serializes correct, especially for the dates : TODO need some standard dateformat to pass around
	 */
	private static Gson gson = null;
	public static Gson getGSON() {
		GsonBuilder gsonBuilder = new GsonBuilder();			
		gsonBuilder.registerTypeAdapter(Date.class,
				new JsonDeserializer<Date>() {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

					@Override
					public Date deserialize(JsonElement arg0, java.lang.reflect.Type arg1,
							JsonDeserializationContext arg2) throws JsonParseException {

						if (arg0.getAsString() == null
								|| arg0.getAsString().length() == 0) {
							return null;
						}

						try {
							return df.parse(arg0.getAsString());
						} catch (ParseException e) {
							System.out.println("error parsing date : " + arg0.getAsString());
							System.out.println(e.getLocalizedMessage());
							return null;
						}
					}
				});
		gsonBuilder.registerTypeAdapter(Date.class,
				new JsonSerializer<Date>() {

					@Override
					public JsonElement serialize(Date arg0,
							java.lang.reflect.Type arg1,
							JsonSerializationContext arg2) {
						// TODO Auto-generated method stub
						return null;
					}
				});
		return gsonBuilder.create();
	}
}
