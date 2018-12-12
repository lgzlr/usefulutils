package utils.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;

/**
 * 解析XML的工具类
 * 注意：所有的xml都是使用dom4j解析的，使用时需要dom4j的支持
 */
public class XmlUtils {

    /**
     * 覆盖默认构造函数,防止通过构造函数得到当前工具类对象
     */
    private XmlUtils(){};

    /**
     * 将接受到的xml字符串转换为dom4j的Document对象
     * @param xmlContent
     * @return
     * @throws DocumentException
     */
    public static Document readXML(String xmlContent) throws DocumentException {
        Document document = DocumentHelper.parseText(xmlContent);
        return document;
    }

    /**
     *  读取指定位置上指定名称节点上的值
     *  注意：返回值已经去除了两端的空格
     * @param fatherNodes 从root节点到指定节点路径上的所有的节点(顺序固定),如果fatherNodes为空，那么默认从root节点读取
     * @param nodeName 需要取值的节点名称
     * @param document dom4j文档对象
     * @return
     */
    public static String readGivenNodeValue(String[] fatherNodes, String nodeName, Document document) throws Exception {
        if (document == null) {
            throw new Exception("传入的Document对象不能为空");
        }
        Element root = document.getRootElement();
        Element fatherElement = root;
        if (fatherNodes != null && fatherNodes.length>0) {
            int i = 0;
            while (i < fatherNodes.length) {
                fatherElement = fatherElement.element(fatherNodes[i]);
            }
        }
        Element targetElement = fatherElement.element(nodeName);
        if (targetElement == null) {
            throw new Exception("传入的Document对象的指定路径下不存在名称为"+nodeName+"的节点");
        }
        return fatherElement.elementTextTrim(nodeName);
    }

    /**
     *  读取指定位置上指定名称节点上的值
     *  注意：返回值已经去除了两端的空格
     * @param fatherNodes 从root节点到指定节点路径上的所有的节点(顺序固定),如果fatherNodes为空，那么默认从root节点读取
     * @param nodeName 需要取值的节点名称
     * @param document dom4j文档对象
     * @return
     * @throws Exception
     */
    public static String readGivenNodeValue(List<String> fatherNodes, String nodeName, Document document) throws Exception  {
        return readGivenNodeValue(fatherNodes.toArray(new String[fatherNodes.size()]), nodeName, document);
    }



}
