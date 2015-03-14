package com.ymiaohuang.mycolck;
/*
 * 
 * 1.���ӵ����
 * �����õ���ListView .. �õ�ArrayAdapter��Calendar��
 * 
 * 2.ͨ��SharedPreferences�洢����
 * SharedPreferences�ӿ���Ҫ�����ȡӦ�ó����Preferences���ݣ�
 * ͨ��key_value��ֵ�Դ������ݡ�
 * ͨ��SharedPreferences��ʵ�������ȡEditor��ɶ����ݵ�д����
 * ���Editor.commit()�ر���Դ��
 * */
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class AlarmView extends LinearLayout {

	private Button btnAddAlarm;
	private ListView lvAlarmList;
	private ArrayAdapter<myAlarm> adapter; 
	public static final String KEY_ALARM_LIST = "alarmList";
	
	public AlarmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AlarmView(Context context) {
		super(context);
	}
	
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		lvAlarmList = (ListView) findViewById(R.id.lvAlarmList);
		
		readSavedAlarmList();
		
		btnAddAlarm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				addAlarm();
			}
		});
		
		adapter = new ArrayAdapter<myAlarm>(getContext(), android.R.layout.simple_list_item_1);
		lvAlarmList.setAdapter(adapter);
		
	}
	
	public void addAlarm(){
		//Calendar�ǳ����ֻ࣬��ͨ��getInstance��ȡ��Ĭ�ϻ�ȡ��Calendarʱ��Ϊ��ǰʱ�䡣
		//����ͨ��c.set(Calendar.HOUR_OF_DAY, hour)������ʱ�䡣
		Calendar c = Calendar.getInstance();
		
		//���ڶԻ���TimePickerDialog��Cotext��callback��Ĭ����ʾ��ʱ�䣬Ĭ����ʾ��ʱ�䣬24Сʱ�ƣ�.show
		new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
			//TimePickerDialog�����ð�ť��
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				//�ж����õ����ӵ�ʱ�䣬���С�����ڣ�����Ϊ�����ʱ�䡣����Ϊ���õ�����ʱ�䡣
				Calendar setTime = Calendar.getInstance();
				setTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
				setTime.set(Calendar.MINUTE,minute);
				
				Calendar nowTime = Calendar.getInstance();
				if(setTime.getTimeInMillis()<=nowTime.getTimeInMillis()){
					setTime.setTimeInMillis(setTime.getTimeInMillis()+24*60*60*1000);//�ӳ�һ�졣
				}
				
				adapter.add(new myAlarm(setTime.getTimeInMillis()));
				saveAlarmList();
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
		
	}
	
	private void saveAlarmList(){
		//��ȡEditor
		//getCount()��ȡItem��������getItem(i)��ȡItem�еĶ���
		//������put��
		Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < adapter.getCount(); i++) {
			sb.append(adapter.getItem(i).getTime()).append(",");
		}
		editor.putString(KEY_ALARM_LIST,sb.toString().substring(0, sb.length()-1));
		editor.commit();
	}
	
	private void readSavedAlarmList(){
		SharedPreferences sp = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);
		String content = sp.getString(KEY_ALARM_LIST, null);

		if (content!=null) {
			String[] timeStrings = content.split(",");
			for (int i = 0; i < timeStrings.length; i++) {
				
				//adapter.add(new myAlarm(Long.parseLong(timeStrings[i])));
				
			}
			
		}
	}
	
	private static class myAlarm{
		private long time = 0;
		private String timeLable = "";
		private Calendar c;
		public myAlarm(long time){
			this.time = time;
			c = Calendar.getInstance();
			c.setTimeInMillis(time);
			timeLable = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
			
		}
		public String toString() {
			return getTimeLable();
		}
		public long getTime(){
			return time;
		}
		public String getTimeLable(){
			return timeLable;
			
		}
	}

}
