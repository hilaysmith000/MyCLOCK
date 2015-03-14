package com.ymiaohuang.mycolck;
/*
 * ��ʱ�ظ�����ĳ�κ�����ʵ��ʱ���ˢ�¡�
 * Handler����Ϣ�Ĵ�����½�һ�����̡߳�
 * ����ʵ����Handler�ಢ��дhandleMassage�������ڷ����ڲ������Ҫ���õķ�����
 * ��handleMassage�ڲ����sendEmptyMessageDelayed(0, 1000)���ظ�����handleMassage������
 * sendEmptyMessage(0)������handleMassage����;
 * */
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout {
	private TextView tvTime;
	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//����Ҫ���õġ�����������ϵͳ���á�
	public TimeView(Context context) {
		super(context);
		
	}
	
	//����Զ���ؼ������뵽XMl�к󴥷�onFinishInflate,���Խ��Զ���ؼ����ӿؼ��ڴ˷��������á�
	protected void onFinishInflate() {
		super.onFinishInflate();
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setTextSize(70);
		timeHandler.sendEmptyMessage(0);
		
	}
	//��View��Visibility���͸ı��ǵ���
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		//�����͹رն�ʱ��
		if (visibility == View.VISIBLE) {
			timeHandler.sendEmptyMessage(0);//0��what��ֵ������������Ϣ��Դ��
		}else{
			timeHandler.removeMessages(0);
		}
	}
	//ˢ��ʱ��
	private void refreshTime(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		String str = format.format(curDate);
		tvTime.setText(str);
	}
	//����һ����ʱ������
	private Handler timeHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			refreshTime();
			if (getVisibility() == View.VISIBLE) {
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			}
		}
	};
}
