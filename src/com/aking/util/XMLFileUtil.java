package com.aking.util;

import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.aking.model.constant.Constants;

/**
 * @author AKing
 * @version 1.0
 */
public class XMLFileUtil {

	private static Logger logger = LogManager.getLogger(XMLFileUtil.class);

	public static Document LoadXmlFile(String filePath) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(filePath);
		} catch (Exception ex) {
			logger.info("Can not load " + filePath);
			logger.debug(ex.getMessage(), ex);
		}
		return doc;
	}

	/**
	 * Get attribute value by name for some xml element.
	 * 
	 * @param element
	 *            Element
	 * @param attributeName
	 *            String
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getAttributeValue(Element element,
			String attributeName) {
		String attributeValue = null;
		for (Iterator<Attribute> i = element.attributeIterator(); i.hasNext();) {
			Attribute attribute = i.next();
			if (attribute.getName().equals(attributeName)) {
				attributeValue = (String) attribute.getData();
				break;
			}
		}
		return attributeValue;
	}

	public static String getNodeText(Element element) {
		if (element != null) {
			return element.getText();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Element findElement(Element searchedElement,
			String targetNodePrefix,
			String targetNodeAttributeName,
			String targetNodeAttributeValue) {
		Element elementTarget = null;
		for (Iterator<Element> i = searchedElement.elementIterator(targetNodePrefix); i.hasNext();) {
			Element element = i.next();
			String strManagerName = XMLFileUtil.getAttributeValue(element, targetNodeAttributeName);
			if (strManagerName.equals(targetNodeAttributeValue)) {
				elementTarget = element;
				break;
			}
		}
		return elementTarget;
	}

	public static String getNodeText(String filePath,
			String targetNodePrefix,
			String targetNodeAttributeName,
			String targetNodeAttributeValue,
			String nodeName) {
		Document document = LoadXmlFile(filePath);
		Element searchedElement = document.getRootElement();
		Element elementTarget = null;
		Iterator<Element> elementIterator = searchedElement.elementIterator(targetNodePrefix);
		for (Iterator<Element> i = elementIterator; i.hasNext();) {
			Element element = i.next();
			String strManagerName = XMLFileUtil.getAttributeValue(element, targetNodeAttributeName);
			if (strManagerName.equals(targetNodeAttributeValue)) {
				elementTarget = element;
				break;
			}
		}
		if (elementTarget == null) {
			return null;
		}
		Element nodeElement = elementTarget.element(nodeName.toLowerCase());
		return getNodeText(nodeElement);
	}

	private XMLFileUtil() {
	}

	public static void main(String[] args) {
		System.out.println(XMLFileUtil.getNodeText(Constants.FUNCTIONMAPPATH, "function", "name", "name", "name"));
	}
}
