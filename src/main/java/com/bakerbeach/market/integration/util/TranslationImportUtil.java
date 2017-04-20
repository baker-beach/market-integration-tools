package com.bakerbeach.market.integration.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bakerbeach.market.core.service.I18NMessageImpl;
import com.bakerbeach.market.translation.api.model.I18NMessage;

import au.com.bytecode.opencsv.CSVReader;

public class TranslationImportUtil {
	protected static final Logger log = LoggerFactory.getLogger(TranslationImportUtil.class);
	
	public static List<I18NMessage> getTranslations(List<Map<String, String>> list) {
		List<I18NMessage> messages = new ArrayList<>();
		
		for(Map<String, String> map : list) {
			try {
				I18NMessageImpl message = new I18NMessageImpl();
				message.setMessageKey(map.get("code"));
				message.setTag(map.get("tag"));
				message.setType(map.get("type"));
				
				message.getMessages().put("de", map.get("de"));
				message.getMessages().put("en", map.get("en"));
				
				messages.add(message);				
			} catch (Exception e) {
				e.getStackTrace();
			}				
		}
		
		return messages;
	}
	
	public static List<Map<String, String>> readCsv(String path) {		
		try {
			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path), "UTF-8"), ';', '\"', CSVReader.DEFAULT_ESCAPE_CHARACTER);
			List<String[]> in = reader.readAll();
			reader.close();
			
			// remove the comment line ---
			in.remove(0);
			
			// get the keys ---
			String[] keys = in.get(0);
			in.remove(0);
			
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			for (String[] values : in) {
				list.add(toMap(keys, values));
			}
			
			return list;
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}
	
	private static Map<String, String> toMap(String[] keys, String[] values) {
		HashMap<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			result.put(keys[i], values[i]);
		}
		return result;
	}


}
