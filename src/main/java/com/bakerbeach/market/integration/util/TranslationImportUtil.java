package com.bakerbeach.market.integration.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bakerbeach.market.core.service.I18NMessageImpl;
import com.bakerbeach.market.translation.api.model.I18NMessage;

public class TranslationImportUtil {
	protected static final Logger log = LoggerFactory.getLogger(TranslationImportUtil.class);

	public static List<I18NMessage> getTranslations(List<Map<String, String>> list) {
		List<I18NMessage> messages = new ArrayList<>();

		for (Map<String, String> map : list) {
			try {
				I18NMessageImpl message = new I18NMessageImpl();

				for (Entry<String, String> e : map.entrySet()) {
					if (StringUtils.equals("code", e.getKey())) {
						message.setMessageKey(e.getValue());
					} else if (StringUtils.equals("tag", e.getKey())) {
						message.setTag(e.getValue());
					} else if (StringUtils.equals("type", e.getKey())) {
						message.setType(e.getValue());
					} else if (StringUtils.isNotBlank(e.getKey()) && StringUtils.isNotBlank(e.getValue())) {
						message.getMessages().put(e.getKey(), e.getValue());
					}

					messages.add(message);
				}
			} catch (Exception e) {
				log.error(ExceptionUtils.getMessage(e));
			}
		}

		return messages;
	}

}
