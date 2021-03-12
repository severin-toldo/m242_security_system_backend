package com.stoldo.accounting_software.shared.util;

import java.util.Arrays;
import org.springframework.core.env.MutablePropertySources;


public class CommonUtils {
	
	public static final String EMAIL_REGEXP = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";
	public static final String EMAIL_REGEXP_MESSAGE = "Invalid email.";
	
	
	public static <E extends Throwable> void falseThenThrow(boolean b, E e) throws E {
		if (!b) {
			throw e;
		}
	}
	
	public static <T, E extends Throwable> T nullThenThrow(T t, E e) throws E {
		if (t != null) {
			return t;
		}
		throw e;
	}
	
	public static boolean isProd(String[] activeProfile) {
		return Arrays.asList(activeProfile).contains("prod");
	}
	
	public static Object getSpringProperty(String name, MutablePropertySources propertySources) {
		return propertySources.stream()
				.map(ps -> ps.getProperty(name))
				.filter(p -> p != null)
				.findFirst()
				.orElse(null);
	}
}
