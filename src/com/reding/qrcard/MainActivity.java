package com.reding.qrcard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {

	
	 ActionBar actionBar;
	 Button editcardButton;
	 ImageView imv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		Context c = MainActivity.this;
		SharedPreferences ini = c.getSharedPreferences("INI", MODE_PRIVATE);
        Editor editor = ini.edit();
		editor.putBoolean("ISFIRST", false);
		editor.commit();
		
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main_activity);
        
        imv = (ImageView) findViewById(R.id.qrcodepic);
        Bitmap bm = getLocalBitmap();
        
        imv.setImageBitmap(bm);
        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
        layout.setAnchorPoint(0.3f);
        
        actionBar = getActionBar();
        actionBar.show();
        
        layout.setPanelSlideListener(new PanelSlideListener() {

            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset < 0.2) {
                    if (getActionBar().isShowing()) {
                        getActionBar().hide();
                    }
                } else {
                    if (!getActionBar().isShowing()) {
                        getActionBar().show();
                    }
                }
            }


            public void onPanelExpanded(View panel) {
            	
            }


            public void onPanelCollapsed(View panel) {

            }


            public void onPanelAnchored(View panel) {

            }
        });
        TextView t = (TextView) findViewById(R.id.brought_by);
        t.setMovementMethod(LinkMovementMethod.getInstance());
        
        editcardButton = (Button)findViewById(R.id.edit_card_button);
        editcardButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setComponent(new ComponentName("com.reding.qrcard", "com.reding.qrcard.EditCardActivity"));
				startActivity(i);
			}
		});
        
        
    }
    
    
    
    //监听back按键。。。
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(this)
			.setTitle("Exit?")
			.setMessage("Do you want to Exit?")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					MainActivity.this.finish();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			})
			.show();
		}
		return super.onKeyDown(keyCode, event);  
		
	}
	
    
    
    
    /* (non-Javadoc)
     * 修改信息后重新加载图片。
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 ImageView imv;
		 imv = (ImageView) findViewById(R.id.qrcodepic);
	     Bitmap bm = getLocalBitmap();
	     imv.setImageBitmap(bm);
	}



	private Bitmap getLocalBitmap() {
		// TODO Auto-generated method stub
        try {
            FileInputStream fis = this.openFileInput("mypic.png");
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

         } catch (FileNotFoundException e) {
        	 Toast.makeText(getApplicationContext(), "Can't find the picture,please edit your infomation first.",
        	 Toast.LENGTH_SHORT).show();
            return null;
       }
	}
	
	//menu 的设置 两个item 一个scan 一个share 启动扫描二维码的activity，share 启动分享
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //添加菜单项
        MenuItem scan=menu.add(0,0,0,"Scan");
        MenuItem share=menu.add(0,0,0,"Share");
        //绑定到ActionBar 
        scan.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        share.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        scan.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				//i.setClassName("com.reding.qrcard.zxing.capture", "com.reding.qrcard.zxing.capture.CaptureActivity");
				i.setComponent(new ComponentName("com.reding.qrcard", "com.reding.qrcard.zxing.capture.CaptureActivity"));
				//if(i==null)
				startActivity(i);
				return true;
			}
		});
        
        
        share.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(Intent.ACTION_SEND); 
				intent.setType("text/plain"); 
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); 
				String text = getResources().getString(R.string.shereto);
				intent.putExtra(Intent.EXTRA_TEXT, text );  
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(Intent.createChooser(intent, getTitle()));
				return true;	
			}
		});
        
        return true;
    }
}