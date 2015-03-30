package com.reding.qrcard;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 *	���������������������
 */
public class WelcomeActivity extends Activity implements OnClickListener,OnPageChangeListener {
	//����ViewPager����
	private ViewPager viewPager;
	
	//����ViewPager������
	private ViewPagerAdapter vpAdapter;
	
	//����һ��ArrayList�����View
	private ArrayList<View> views;

	//����ͼƬ��Դ
    private static final int[] pics = {R.drawable.page1,R.drawable.page2,R.drawable.page3,R.drawable.page4};
    
    //�ײ�С���ͼƬ
    private ImageView[] points;
    
    //��¼��ǰѡ��λ��
    private int currentIndex;
    private int needjump = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("welcome no pointer", "43");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		
		Log.d("welcome no pointer", "48");
		
		setContentView(R.layout.activity_welcome);
		
		Log.d("welcome no pointer", "52");
		
		initView();
		Log.d("welcome no pointer", "55");
		initData();	
		Log.d("welcome no pointer", "57");
	}

	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ����ArrayList����
		views = new ArrayList<View>();
		Log.d("welcome no pointer", "66");
		
		//ʵ����ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		Log.d("welcome no pointer", "70");
		
		//ʵ����ViewPager������
		vpAdapter = new ViewPagerAdapter(views);
		Log.d("welcome no pointer", "74");
	}
	
	/**
	 * ��ʼ������
	 */
	private void initData(){
		//����һ�����ֲ����ò���
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                														  LinearLayout.LayoutParams.MATCH_PARENT);
		Log.d("welcome no pointer", "84");
        //��ʼ������ͼƬ�б�
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        } 
        Log.d("welcome no pointer", "92");
        
        //��������
        viewPager.setAdapter(vpAdapter);
        Log.d("welcome no pointer", "96");
        //���ü���
        viewPager.setOnPageChangeListener(this);
        Log.d("welcome no pointer", "99");
        //��ʼ���ײ�С��
        initPoint();
	}
	
	/**
	 * ��ʼ���ײ�С��
	 */
	private void initPoint(){
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);       
		Log.d("welcome no pointer", "109");
        points = new ImageView[pics.length];
        Log.d("welcome no pointer", "111");
        //ѭ��ȡ��С��ͼƬ
        for (int i = 0; i <pics.length; i++) {

        	//�õ�һ��LinearLayout�����ÿһ����Ԫ��
        	points[i] = (ImageView) linearLayout.getChildAt(i);
        	//Ĭ�϶���Ϊ��ɫ
        	points[i].setEnabled(true);
        	//��ÿ��С�����ü���
        	points[i].setOnClickListener(this);
        	//����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
        	points[i].setTag(i);
        }
        Log.d("welcome no pointer", "123");
        //���õ���Ĭ�ϵ�λ��
        currentIndex = 0;
        Log.d("welcome no pointer", "126");
        //����Ϊ��ɫ����ѡ��״̬
        points[currentIndex].setEnabled(false);
        Log.d("welcome no pointer", "129");
	}
	
	/**
	 * ������״̬�ı�ʱ����
	 */
	public void onPageScrollStateChanged(int arg0) {
		if(currentIndex==3){
			
			needjump = 1;
			if(needjump==1)
			{
				Intent mainIntent = new Intent(WelcomeActivity.this,MainActivity.class);
				WelcomeActivity.this.startActivity(mainIntent);
				this.finish();
				return;
			}
		}
	}
	
	/**
	 * ����ǰҳ�汻����ʱ����
	 */

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		

		Log.d("number of main !!!", "number ++");
	}
	
	/**
	 * ���µ�ҳ�汻ѡ��ʱ����
	 */

	public void onPageSelected(int position) {
		//���õײ�С��ѡ��״̬
        setCurDot(position);
        Log.d("welcome no pointer", "154");
        //String s = String.valueOf(position);
        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}

	/**
	 * ͨ������¼����л���ǰ��ҳ��
	 */
	public void onClick(View v) {
		 int position = (Integer)v.getTag();
		 Log.d("welcome no pointer", "162");
         setCurView(position);
         Log.d("welcome no pointer", "164");
         setCurDot(position);		
         Log.d("welcome no pointer", "166");
	}

	/**
	 * ���õ�ǰҳ���λ��
	 */
	private void setCurView(int position){
         if (position < 0 || position >= pics.length) {
             return;
         }
         Log.d("welcome no pointer", "176");
         viewPager.setCurrentItem(position);
         Log.d("welcome no pointer", "178");
     }

     /**
     * ���õ�ǰ��С���λ��
     */
    private void setCurDot(int positon){
         if (positon < 0 || positon >= pics.length || currentIndex == positon) {
             return;
         }
         Log.d("welcome no pointer", "188");
         points[positon].setEnabled(false);
         Log.d("welcome no pointer", "190");
         points[currentIndex].setEnabled(true);
         Log.d("welcome no pointer", "192");
         currentIndex = positon;
     }
	
}
