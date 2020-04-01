package aido.OutPan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OutpanObject {

	public String
		gtin,
		outpan_url,
		name;
	
	public HashMap<String, String>
		attributes;
	
	public ArrayList<String>
		images,
		videos;
	
	public OutpanObject() {
		this.gtin = "";
		this.outpan_url = "";
		this.name = "";
		
		this.attributes = new HashMap<String, String>();
		this.images = new ArrayList<String>();
		this.videos = new ArrayList<String>();
	}


	String[] getNameArray(JSONObject object)
	{
		List<String> keyList = new ArrayList<String>();
		for (Iterator<String> it = object.keys(); it.hasNext(); ) {
			String key = it.next();
			keyList.add(key);
		}

		String[] keyArray = keyList.toArray(new String[keyList.size()]);


		return keyArray;
	}
	
	public OutpanObject(JSONObject json) {
		this();

		try {
			this.gtin = json.getString("gtin");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			this.outpan_url = json.getString("outpan_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (!json.isNull("name"))
			try {
				this.name = json.getString("name");
			} catch (JSONException e) {
				e.printStackTrace();
			}


		/*if (!json.isNull("attributes")) {
			JSONObject attrObject = null;
			try {
				attrObject = json.getJSONObject("attributes");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			String[] attrs;


			attrs = getNameArray(attrObject);//JSONObject.getNames(attrObject);
			
			for (int a = 0; a < attrs.length; a++)
				try {
					this.attributes.put(attrs[a], attrObject.getString(attrs[a]));
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}*/
		
		if (!json.isNull("images")) {
			JSONArray imgs = null;
			try {
				imgs = json.getJSONArray("images");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < imgs.length(); i++)
				try {
					this.images.add(imgs.getString(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}
		
		if (!json.isNull("videos")) {
			JSONArray vids = null;
			try {
				vids = json.getJSONArray("videos");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < vids.length(); i++)
				try {
					this.videos.add(vids.getString(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}
	}
}