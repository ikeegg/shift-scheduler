package com.pubint.projInit.base;

import java.io.Serializable;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.xpath.XPath;

import org.w3c.dom.Element;

public class Base implements Serializable {
	private static final long serialVersionUID = 5L;

	public String rjlzf(String data, int length) {
		String retVal = data;

		if (data.trim().length() < length) {
			try {
				@SuppressWarnings("unused") long numericData = Long.parseLong(data);

				retVal = data.trim();

				while (retVal.length() < length) {
					retVal = "0" + retVal;
				}
			} catch (Exception ex) {

			}
		}

		return retVal;
	}

	public String pad(String s, int length) {
		return pad(s, length, ' ').substring(0, length);
	}

	public String pad(String s, int length, char padChar) {
		if (isEmptyString(s)) {
			s = "";
		}

		String result = s;

		if (length > s.length()) {
			for (int loop = 0; loop < (length - s.length()); loop++) {
				result += padChar;
			}
		} else {
			result = result.substring(0, length);
		}

		return result;
	}

	public String toEscaped(String replaceString) {
		if (isEmptyString(replaceString)) {
			return "";
		}

		String work = replaceString.replaceAll("\\\\", "\\\\\\\\");
		work = work.replaceAll("'", "\\\\'");
		work = work.replaceAll("\"", "\\\\\"");

		return work;
	}

	public String fixXML(String xml) {
		return xml.replace("&", "&amp;");
	}

	public String fixXML(StringWriter xml) {
		return xml.toString().replace("&", "&amp;");
	}

	public String fixXML(StringBuilder xml) {
		return xml.toString().replace("&", "&amp;");
	}

	public String toNumericEscaped(String replaceString) {
		if (isEmptyString(replaceString)) {
			return "";
		}

		String work = "";

		for (char x : replaceString.toCharArray()) {
			if (".0123456789-".indexOf(x) != - 1) {
				work += x;
			}
		}

		return work;
	}

	public long getLong(Element node, String elementName, XPath xpath) {
		try {
			String attribute = xpath.evaluate("//" + elementName, node);
			String work = toNumericEscaped(attribute);

			return Long.parseLong(work);
		} catch (Exception e) {
			return 0;
		}
	}

	public int getInt(Element node, String elementName, XPath xpath) {
		try {
			String attribute = xpath.evaluate("//" + elementName, node);
			String work = toNumericEscaped(attribute);

			return Integer.parseInt(work);
		} catch (Exception e) {
			return 0;
		}
	}

	public String getString(Element node, String elementName, XPath xpath) {
		String attribute = "";

		try {
			String raw = xpath.evaluate("//" + elementName, node);
			String data = "";

			if (raw != null) {
				int plusPos = raw.indexOf("+");

				while (plusPos >= 0) {
					raw = raw.substring(0, plusPos) + "%2B" + raw.substring(plusPos + 1);

					plusPos = raw.indexOf("+");
				}

				int slashPos = raw.indexOf("~1~");

				while (slashPos >= 0) {
					raw = raw.substring(0, slashPos) + "\\" + raw.substring(slashPos + 3);
					slashPos = raw.indexOf("~1~");
				}

				int pctPos = raw.indexOf("~2~");

				while (pctPos >= 0) {
					raw = raw.substring(0, pctPos) + "%25" + raw.substring(pctPos + 3);
					pctPos = raw.indexOf("~2~");
				}

				int aposPos = raw.indexOf("~3~");

				while (aposPos >= 0) {
					raw = raw.substring(0, aposPos) + "\'" + raw.substring(aposPos + 3);
					aposPos = raw.indexOf("~3~");
				}

				int rposPos = raw.indexOf("~4~");

				while (rposPos >= 0) {
					raw = raw.substring(0, rposPos) + "\n" + raw.substring(rposPos + 3);
					rposPos = raw.indexOf("~4~");
				}
			}

			try {
				data = URLDecoder.decode(raw, "UTF-8");
			} catch (Exception e) {

			}

			attribute = data;
		} catch (Exception e) {

		}

		attribute = attribute.trim();

		return attribute;
	}

	public double getReal(Element node, String elementName, XPath xpath) {
		try {
			String attribute = xpath.evaluate("//" + elementName, node);
			String work = toNumericEscaped(attribute);

			return Double.parseDouble(work);
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean getBoolean(Element node, String elementName, XPath xpath) {
		try {
			String attribute = xpath.evaluate("//" + elementName, node);

			if (("true".equalsIgnoreCase(attribute)) || ("yes".equalsIgnoreCase(attribute))) {
				return true;
			}

			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Date getDate(Element element, String attribute, XPath xpath) {
		return toDate(getString(element, attribute, xpath));
	}

	public long toLong(String stringLong) {
		try {
			String work = toNumericEscaped(stringLong);

			return (isEmptyString(work) ? 0 : Long.parseLong(work));
		} catch (NumberFormatException nfex) {
			return 0;
		}
	}

	public int toInt(String stringInt) {
		try {
			String work = toNumericEscaped(stringInt);

			return (isEmptyString(work) ? 0 : Integer.parseInt(work));
		} catch (NumberFormatException nfex) {
			return 0;
		}
	}

	public double toDouble(String stringDouble) {
		try {
			String work = toNumericEscaped(stringDouble);

			return (isEmptyString(work) ? 0 : Double.parseDouble(work));
		} catch (NumberFormatException nfex) {
			return 0;
		}
	}

	public String toString(String someString, String defaultValue) {
		return (someString == null) ? defaultValue.trim() : someString.trim();
	}

	public String toString(String someString) {
		return toString(someString, "");
	}

	public Date toDate(String date) {
		String dateFormat = "MM/dd/yy";

		if (date.indexOf("-") >= 0) {
			dateFormat = "yyyy-MM-dd";
		}

		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);

			return new Date((format.parse(date)).getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public boolean notEmptyString(String string) {
		return (! isEmptyString(string));
	}

	public boolean isEmptyString(String string) {
		return ((string == null) || "".equals(string.trim()));
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;

		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}

		return daysBetween;
	}
}
