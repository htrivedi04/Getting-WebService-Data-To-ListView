package com.example.harshit.songplaylist;

/**
 * Created by 34148151 on 3/31/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomizedListView extends Activity {
    // All static variables
    //static final String URL = "http://api.androidhive.info/music/music.xml";
    //static final String URL = "http://66.3.85.92/test/testabc.xml";
    static final String URL = "http://66.3.85.92/test/myWebService.asmx/GetEmployees";

    // XML node keys
    //static final String KEY_SONG = "song"; // parent node
    static final String KEY_SONG = "Table1"; // parent node

    static final String KEY_ID = "EMP_NO";

    //static final String KEY_TITLE = "title";
    static final String KEY_TITLE = "EMP_NAME_P";

    //static final String KEY_ARTIST = "artist";
    static final String KEY_ARTIST = "EMP_HAY_DES";

    static final String KEY_DEPT = "EMP_DEPT";

    //static final String KEY_DURATION = "duration";
    static final String KEY_DURATION = "EMP_DESK_NO";

    //static final String KEY_THUMB_URL = "thumb_url";
    static final String KEY_THUMB_URL = "IMG_URL";

    ListView list;
    LazyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        xml = StringEscapeUtils.unescapeHtml(xml);

        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_SONG);
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            //map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_DEPT, parser.getValue(e, KEY_DEPT));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

            // adding HashList to ArrayList
            songsList.add(map);
        }
        list=(ListView)findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
    }
}
