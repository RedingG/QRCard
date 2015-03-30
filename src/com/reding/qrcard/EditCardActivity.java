package com.reding.qrcard;




import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class EditCardActivity extends Activity {
	
	String[] title = {"Name","Tel number","Cellphone","Fax","QQ","Web page","E-mail","Company","Title","Address","Birthday","Introduction"};
    int[] imgid_h = {R.drawable.edit_01_h,R.drawable.edit_02_h,R.drawable.edit_03_h,R.drawable.edit_04_h,R.drawable.edit_05_h,R.drawable.edit_06_h,R.drawable.edit_07_h,R.drawable.edit_08_h,R.drawable.edit_09_h,R.drawable.edit_10_h,R.drawable.edit_11_h,R.drawable.edit_12_h};
	int[] imgid = {R.drawable.edit_01,R.drawable.edit_02,R.drawable.edit_03,R.drawable.edit_04,R.drawable.edit_05,R.drawable.edit_06,R.drawable.edit_07,R.drawable.edit_08,R.drawable.edit_09,R.drawable.edit_10,R.drawable.edit_11,R.drawable.edit_12};
	MenuItem OKItem = null;
	int changedflag = 0;

	//
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        
        //code for the grid view ,
        GridView gridview = (GridView) findViewById(R.id.gridview_editcard);
        
        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        //
        
		Context c = EditCardActivity.this;
		SharedPreferences in = c.getSharedPreferences("INFO", MODE_PRIVATE);
        //and picture for each item,set its text 
        for(int i=1;i<13;i++)
        {
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	String tmp =in.getString(title[i-1], "");
        	if(tmp.equals(""))
        	{
            	map.put("ItemImage", imgid_h[i-1]);//添加图像资源的ID
        	}else{
            	map.put("ItemImage", imgid[i-1]);//添加图像资源的ID
        	}

			map.put("ItemText", title[i-1]);//按序号做ItemText
        	lstImageItem.add(map);
        }
        
        
        
        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(this, //没什么解释
        		                                    lstImageItem,//数据来源 
        		                                    R.layout.carditem,//night_item的XML实现
        		                                    
        		                                    //动态数组与ImageItem对应的子项        
        		                                    new String[] {"ItemImage","ItemText"}, 
        		                                    
        		                                    //ImageItem的XML文件里面的一个ImageView,两个TextView ID
        		                                    new int[] {R.id.ItemImage,R.id.ItemText});
        
        //添加并且显示
        gridview.setAdapter(saImageItems);
        //添加消息处理
        gridview.setOnItemClickListener(new ItemClickListener());
    }
    
    
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Bundle savedInstanceState = null;
		this.onCreate(savedInstanceState);
	}

	//处理返回键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(OKItem!=null&&changedflag==1)
			{
				creatPicture();
				//Toast.makeText(this, "Creat picture after back key clicked", Toast.LENGTH_LONG).show();
				EditCardActivity.this.finish();
			}
		}
		
		return super.onKeyDown(keyCode, event);  
		
	}
	
	

	//当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
    class  ItemClickListener implements OnItemClickListener
    {
		public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened 
				                          View arg1,//The view within the AdapterView that was clicked
				                          int arg2,//The position of the view in the adapter
				                          long arg3//The row id of the item that was clicked
				                          ) {
			//在本例中arg2=arg3
			@SuppressWarnings("unchecked")
			HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);
			//显示所选Item的ItemText
			String s = (String)item.get("ItemText");
			setTitle((String)item.get("ItemText"));
			
			enterEdit(s);
			
		}
    }
	///
    
    void enterEdit(final String s){
    	
    	CharSequence value;
    	Context ctx = EditCardActivity.this;       
        final SharedPreferences info = ctx.getSharedPreferences("INFO", MODE_PRIVATE);
        final EditText et = new EditText(this);
        value = info.getString(s, "");
        et.setText(value);
        
    	new AlertDialog.Builder(this).setTitle("Enter "+s).setIcon(
    		     android.R.drawable.ic_dialog_info)
    		     .setView(et
    		     ).setPositiveButton("Save", new OnClickListener() {
    		    	 public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
    		    		 SharedPreferences.Editor editor = info.edit();
    		    		 editor.putString(s, et.getText().toString());
    		    		 editor.commit();
    		    		 EditCardActivity.this.onResume();
    		    		 changedflag = 1;
					}
				})
    		     .setNegativeButton("Cancel", null).show();
    	
    }
    
    
    //code for the menu
	@Override	
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //添加菜单项
        //final MenuItem OKItem=menu.add(0,0,0,"OK");
        OKItem=menu.add(0,0,0,"OK");
        //绑定到ActionBar 
        OKItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        OKItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				//here to create the qrcode picture on the sdcard
				//
//				String topic ;
//				Bitmap pictosave = null;
//				FileOutputStream fos = null;
//				Context ctx = EditCardActivity.this;
//				SharedPreferences info = ctx.getSharedPreferences("INFO", MODE_PRIVATE);
//				topic = "BEGIN:VCARD\nVERSION:3.0\n"+
//						"N:" + info.getString("Name", "") + "\nEMAIL:"+info.getString("E-mail","" )+"\nTEL:"+info.getString("Tel number", "")+"\nTEL;CELL:"+info.getString("Cellphone", "")+"\nTEL;FAX:"+info.getString("Fax", "")+"\nX-QQ:"+info.getString("QQ", "")+"\nADR:"+info.getString("Address", "")+
//						"\nWORK:"+info.getString("Company", "")+"\nTITLE:"+info.getString("Title", "")+"\nURL:"+info.getString("Web page", "")+"\nNOTE:"+info.getString("Introduction", "")+"\nBDAY:"+info.getString("Birthday", "")+"\nEND:VCARD\n";
//				
//				try {
//					pictosave = Creat2DCode(topic);
//					fos = new FileOutputStream(Environment.getExternalStorageDirectory()+"/1.png");
//					 if (fos != null) {  
//			                pictosave.compress(Bitmap.CompressFormat.JPEG, 100, fos); 
//			                fos.flush();
//			                fos.close(); 
//					 }
//				} catch (WriterException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				 catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
				creatPicture();
				//
				EditCardActivity.this.finish();
				return true;
			}
		});
        return true;
    }
	
	
	public void creatPicture(){
		String topic ;
		Bitmap pictosave = null;
		
		//change here to put the picture on /data/data
		FileOutputStream fos;

		
		//FileOutputStream fos = null;
		Context ctx = EditCardActivity.this;
		SharedPreferences info = ctx.getSharedPreferences("INFO", MODE_PRIVATE);
		topic = "BEGIN:VCARD\nVERSION:3.0\n"+
				"N:" + info.getString("Name", "") + "\nEMAIL:"+info.getString("E-mail","" )+"\nTEL:"+info.getString("Tel number", "")+"\nTEL;CELL:"+info.getString("Cellphone", "")+"\nTEL;FAX:"+info.getString("Fax", "")+"\nX-QQ:"+info.getString("QQ", "")+"\nADR:"+info.getString("Address", "")+
				"\nWORK:"+info.getString("Company", "")+"\nTITLE:"+info.getString("Title", "")+"\nURL:"+info.getString("Web page", "")+"\nNOTE:"+info.getString("Introduction", "")+"\nBDAY:"+info.getString("Birthday", "")+"\nEND:VCARD\n";
		
		try {
			pictosave = Creat2DCode(topic);
			fos = this.openFileOutput("mypic.png", Context.MODE_PRIVATE);
			//fos = new FileOutputStream(Environment.getDataDirectory()+"/mypic.png");
			 if (fos != null) {  
	                pictosave.compress(Bitmap.CompressFormat.JPEG, 100, fos); 
	                fos.flush();
	                fos.close(); 
			 }
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
    @SuppressWarnings("unchecked")
	public Bitmap Creat2DCode(String s) throws WriterException{
    	@SuppressWarnings("rawtypes")
		Hashtable hints = new Hashtable();
        
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    	
    	BitMatrix matrix = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 400, 400,hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width*height];
		for(int y = 0;y<height;y++){
			for(int x = 0;x<width;x++)
			{
				if(matrix.get(x, y))
				{
					 pixels[y * width + x] = 0xffffffff;
				}
			}
		}
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		
		
		int bitmap_w=bitmap.getWidth();
		int bitmap_h=bitmap.getHeight();
		
		for(int i=0;i<bitmap_h;i++)
		{
			for(int j=0;j<bitmap_w;j++)
			{
		        int color1=bitmap.getPixel(j,i);
		        if(color1 == 0xffffffff)
		        bitmap.setPixel(j, i, 0xff000000 );
		        else{
		        	bitmap.setPixel(j, i, 0xffffffff);
		        }
		     }
		 }
		return bitmap;
    }
	
}
