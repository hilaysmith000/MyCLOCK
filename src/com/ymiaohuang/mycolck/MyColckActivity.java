package com.ymiaohuang.mycolck;
/*
 * �๦��ʱ�ӣ�ʹ��TabHost���֡�
 * ����ѧϰTabHost���ֵ�ʹ�á�
 * */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MyColckActivity extends Activity {
    private TabHost tabHost;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();//��ʼtabHost
        
        //��˳���tabHost��ӱ������ѡ��������ѡ����ָʾ�������⣩�����ݡ�
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("����").setContent(R.id.tabAlarm));
        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("ʱ��").setContent(R.id.tabTime));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("��ʱ��").setContent(R.id.tabTimer));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("���").setContent(R.id.tabStopWatch));
    }
}