package com.example.harshit.songplaylist;

/**
 * Created by 34148151 on 3/31/2016.
 */

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.text.Html;
import android.util.Log;

public class XMLParser {

    // constructor
    public XMLParser() {

    }

    /**
     * Getting XML from URL making HTTP request
     *
     * @param url string
     */
    public String getXmlFromUrl(String url) {
        String xml = null;

        try {
            xml = new RequestTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }

//    public final String getElementValue(Node elem) {
//        Node child;
//        if (elem != null) {
//            if (elem.hasChildNodes()) {
//                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
//                    if (child.getNodeType() == Node.TEXT_NODE) {
//
//                        return child.getNodeValue();
//                    }
//                }
//            }
//        }
//        return "";
//    }

    public final String getElementValue( Node elem ) {
        Node child;
        String returnValue = "" ;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE ){
                        // String returnValue =  child.getNodeValue();
                        returnValue +=  child.getTextContent();
                    }

                }
                //   Log.e("ReturnValue", Html.fromHtml(returnValue).toString());
                return Html.fromHtml(returnValue).toString();
            }
        }
        return "";
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
}

