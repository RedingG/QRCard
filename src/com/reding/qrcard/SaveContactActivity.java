package com.reding.qrcard;

import java.util.ArrayList;
import java.util.HashMap;


import com.reding.qrcard.core.CallingCard;
import com.reding.qrcard.core.RegularExpression;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Identity;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SaveContactActivity extends Activity {
	
	ListView lv;
	//
    CallingCard cc;
	//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_contact);
		
		
		lv = (ListView) findViewById(R.id.listView1);
		
		Context ctx = SaveContactActivity.this;
		SharedPreferences result = ctx.getSharedPreferences("RESULT", MODE_PRIVATE);
		String rt = result.getString("result", "");
		Log.d("Look arrayOutofIndexException", "before regular Expression");
		//Toast.makeText(ctx, rt,Toast.LENGTH_LONG).show();
		cc = new RegularExpression().regularEp(rt);
		//
		Log.d("Look arrayOutofIndexException", "after regular Expression");
		
		 ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		 
		 final String[] itemTitle = {"Name","Tele","Mobile","Fax","QQ","Web page","Email","Company","Title","Address","Note","Birthday"};
		 String[] itemText = {cc.getName(),cc.getTel(),cc.getMobile(),cc.getFax(),cc.getIm(),cc.getWeb(),cc.getEmail(),cc.getCompany(),cc.getPosition(),cc.getAddress(),cc.getRemark(),cc.getBirthday().toString()};
		 
		 //Toast.makeText(ctx, cc.toString(),Toast.LENGTH_LONG).show();
		 
	        for(int i=0;i<itemText.length||i<itemTitle.length;i++)
	        {
	        	HashMap<String, Object> map = new HashMap<String, Object>();
	        	map.put("ItemTitle", itemTitle[i]);
	        	map.put("ItemText", itemText[i]);
	        	listItem.add(map);
	        }
	        
	        Log.d("Look arrayOutofIndexException", "OnCreate");
	        //生成适配器的Item和动态数组对应的元素
	        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
	            R.layout.list_view_layout,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"ItemImage","ItemTitle", "ItemText"}, 
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}
	        );
	       
	        Log.d("Look arrayOutofIndexException", "添加并且显示");
	        //添加并且显示
	        lv.setAdapter(listItemAdapter);
	        
	        Log.d("Look arrayOutofIndexException", "添加点击");
	        //添加点击
	        lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					setTitle(itemTitle[arg2]);
				}

			});
	        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_contact, menu);
        MenuItem save=menu.add(0,0,0,"Save");
        MenuItem cancel=menu.add(0,0,0,"Cancel");
        save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        cancel.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        Log.d("Look arrayOutofIndexException", "onCreateOptionsMenu");
        save.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				saveContact(cc);
				Toast.makeText(SaveContactActivity.this,"Contact has been add to contact list!",Toast.LENGTH_SHORT).show();
				SaveContactActivity.this.finish();
				return true;
			}
		});
        cancel.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				SaveContactActivity.this.finish();
				return true;
			}
		});    
		return true;
	}
	
	@SuppressLint("InlinedApi")
	private void saveContact(CallingCard cc){
		Log.d("Look arrayOutofIndexException", "saveContact");
		ContentValues values = new ContentValues (); 
		Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI,values); 
		long rawContactsId = ContentUris.parseId(rawContactUri); 
		
		
		values.clear(); 
		values.put(StructuredName.RAW_CONTACT_ID,rawContactsId); 
		values.put(Data.MIMETYPE,StructuredName.CONTENT_ITEM_TYPE); 
		values.put(StructuredName.DISPLAY_NAME,cc.getName()); 
	    getContentResolver().insert(Data.CONTENT_URI,values); 
	    Log.d("Look arrayOutofIndexException", "put name");
	    
	    values.clear(); 
	    values.put(Phone.RAW_CONTACT_ID,rawContactsId); 
	    //String "Data.MIMETYPE":The MIME type of the item represented by this row
	    //String "CONTENT_ITEM_TYPE": MIME type used when storing this in data table.
	    values.put(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE); 
	    values.put(Phone.NUMBER,cc.getTel()); 
	    values.put(Phone.TYPE,Phone.TYPE_HOME);
	    getContentResolver().insert(Data.CONTENT_URI,values); 
	    Log.d("Look arrayOutofIndexException", "put tele");
	    
	    
	    
	    values.clear(); 
	    values.put(Phone.RAW_CONTACT_ID,rawContactsId); 
	    //String "Data.MIMETYPE":The MIME type of the item represented by this row
	    //String "CONTENT_ITEM_TYPE": MIME type used when storing this in data table.
	    values.put(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE); 
	    values.put(Phone.NUMBER, cc.getMobile());
	    values.put(Phone.TYPE, Phone.TYPE_MOBILE);
	    getContentResolver().insert(Data.CONTENT_URI,values);
	    Log.d("Look arrayOutofIndexException", "put mobile");
	    
	    
	    values.clear(); 
	    values.put(Phone.RAW_CONTACT_ID,rawContactsId); 
	    //String "Data.MIMETYPE":The MIME type of the item represented by this row
	    //String "CONTENT_ITEM_TYPE": MIME type used when storing this in data table.
	    values.put(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE); 
	    values.put(Phone.NUMBER, cc.getFax());
	    values.put(Phone.TYPE, Phone.TYPE_FAX_WORK);
	    getContentResolver().insert(Data.CONTENT_URI,values);
	    Log.d("Look arrayOutofIndexException", "put fax");
	    
	    
	    
	    values.clear(); 
	    values.put(Phone.RAW_CONTACT_ID,rawContactsId); 
	    //String "Data.MIMETYPE":The MIME type of the item represented by this row
	    //String "CONTENT_ITEM_TYPE": MIME type used when storing this in data table.
	    values.put(Data.MIMETYPE,Event.CONTENT_ITEM_TYPE); 
	    values.put(Event.START_DATE, cc.getBirthday().toString());
	    values.put(Event.TYPE, Event.TYPE_BIRTHDAY);
	    getContentResolver().insert(Data.CONTENT_URI,values);
	    Log.d("Look arrayOutofIndexException", "put bday");
	    
        values.clear();  
        values.put(Data.RAW_CONTACT_ID, rawContactsId);  
        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);  
        values.put(Email.DATA,cc.getEmail());  
        values.put(Email.TYPE, Email.TYPE_WORK); 
	    getContentResolver().insert(Data.CONTENT_URI,values); 
	    Log.d("Look arrayOutofIndexException", "email");
	    
	    
        values.clear();  
        values.put(Data.RAW_CONTACT_ID, rawContactsId);  
        values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);  
        values.put(Organization.DATA,cc.getCompany());  
        values.put(Organization.TYPE, Organization.TYPE_WORK); 
	    getContentResolver().insert(Data.CONTENT_URI,values); 
	    Log.d("Look arrayOutofIndexException", "companty");
	    
        values.clear();  
        values.put(Data.RAW_CONTACT_ID, rawContactsId);  
        values.put(Data.MIMETYPE, Note.CONTENT_ITEM_TYPE);  
        values.put(Note.NOTE,cc.getRemark());
	    getContentResolver().insert(Data.CONTENT_URI,values); 
	    Log.d("Look arrayOutofIndexException", "put note");
	    
	    values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactsId);  
        values.put(Data.MIMETYPE, Im.CONTENT_ITEM_TYPE);  
        values.put(Im.DATA,cc.getIm());  
        values.put(Im.TYPE, Im.PROTOCOL_QQ); 
	    getContentResolver().insert(Data.CONTENT_URI,values); 
	    Log.d("Look arrayOutofIndexException", "put");
	    
	    values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactsId);  
        values.put(Data.MIMETYPE, Website.CONTENT_ITEM_TYPE);  
        values.put(Website.DATA,cc.getWeb());  
        values.put(Website.TYPE, Website.TYPE_WORK); 
	    getContentResolver().insert(Data.CONTENT_URI,values); 
	    Log.d("Look arrayOutofIndexException", "put web");
	    
	    values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactsId);  
        values.put(Data.MIMETYPE, Identity.CONTENT_ITEM_TYPE);  
        values.put(Identity.IDENTITY,cc.getPosition());
        values.put(Identity.CONTENT_ITEM_TYPE,Identity.NAMESPACE);
	    getContentResolver().insert(Data.CONTENT_URI,values);
	    Log.d("Look arrayOutofIndexException", "put title");
	    
	    
	    values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactsId);  
        values.put(Data.MIMETYPE, StructuredPostal.CONTENT_ITEM_TYPE);  
        values.put(StructuredPostal.DATA,cc.getAddress());  
        values.put(StructuredPostal.TYPE, StructuredPostal.TYPE_WORK); 
	    getContentResolver().insert(Data.CONTENT_URI,values);
	    Log.d("Look arrayOutofIndexException", "put address");
	
	}	
}



