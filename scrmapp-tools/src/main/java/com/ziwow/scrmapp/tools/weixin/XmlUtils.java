package com.ziwow.scrmapp.tools.weixin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thoughtworks.xstream.XStream;

public class XmlUtils {

	public static final XmlMapper mapper = new XmlMapper();
	

	/** The Constant logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(XmlUtils.class);
	
	private XmlUtils() {
	}
	
	public static <T> T xmlToObject(String xml, Class<T> clz){
		XStream xs = XStreamAdaptor.createXstream();
        xs.ignoreUnknownElements();
        xs.alias("xml", clz);
        T classE = (T) xs.fromXML(xml);
        return classE;
	}

	public static String object2Xml(Object object) throws IOException {
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.writeValueAsString(object);
	}

	public static <T> T xml2Object(String xml, Class<T> clz) throws IOException{
		return mapper.readValue(xml, clz);
	}
	
	public static String object2XmlByJAXB(Object object, String charset,
			Boolean format, Boolean fragment) {
		
		StringWriter writer = new StringWriter();
		
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			// create marshaller
			Marshaller marshaller = context.createMarshaller();
			// specify the output encoding in the marshalled XML data.
			marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
		    // specify whether or not the marshalled XML data is formatted with linefeeds and indentation
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
			// specify whether or not the marshaller will generate declaration region into XML data
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
			
			marshaller.marshal(object, writer);
			
		} catch (JAXBException e) {
			
			LOG.error("Fail to convert object[" + object + "] to XML.", e);
		}
		return writer.toString();
	}
	
	@SuppressWarnings("all")
	public static <T> T xml2ObjectByJAXB(String object, Class<T> clz) {
		
		T result = null;
		
		try {
			JAXBContext context = JAXBContext.newInstance(clz);
			// create unmarshaller
			Unmarshaller unmarshaller = context.createUnmarshaller();
			result =  (T) unmarshaller.unmarshal(new StringReader(object));
		} catch (JAXBException e) {

			LOG.error("Fail to convert xml[" + object + "] to object[" + clz + "].", e);
		}
		return result;
	}
	
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws JDOMException, IOException {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
	
	/**
	 * 获取xml编码字符集
	 * @param strxml
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		in.close();
		return (String)doc.getProperty("encoding");
	}
}
