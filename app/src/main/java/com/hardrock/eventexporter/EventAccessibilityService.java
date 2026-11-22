package com.hardrock.eventexporter;
import android.accessibilityservice.*;
import android.graphics.Path;
import android.os.*;
import android.view.accessibility.*;
import java.util.*;
public class EventAccessibilityService extends AccessibilityService {
 public static EventAccessibilityService instance;private final Handler handler=new Handler(Looper.getMainLooper());private boolean running=false;private int steps=0,unchanged=0;private String previous="";
 @Override protected void onServiceConnected(){super.onServiceConnected();instance=this;}
 @Override public void onDestroy(){stopScan();instance=null;super.onDestroy();}
 @Override public void onAccessibilityEvent(AccessibilityEvent e){} @Override public void onInterrupt(){stopScan();}
 public void startScan(){running=true;steps=0;unchanged=0;previous="";handler.postDelayed(()->tick(),5000);}
 public void stopScan(){running=false;handler.removeCallbacksAndMessages(null);}
 private void collect(AccessibilityNodeInfo node,List<String> lines){if(node==null)return;if(node.getText()!=null)lines.add(node.getText().toString());if(node.getContentDescription()!=null)lines.add(node.getContentDescription().toString());for(int i=0;i<node.getChildCount();i++)collect(node.getChild(i),lines);}
 private AccessibilityNodeInfo scrollable(AccessibilityNodeInfo n){if(n==null)return null;for(int i=0;i<n.getChildCount();i++){AccessibilityNodeInfo f=scrollable(n.getChild(i));if(f!=null)return f;}return n.isScrollable()?n:null;}
 private void tick(){if(!running)return;AccessibilityNodeInfo root=getRootInActiveWindow();if(root==null){stopScan();return;}String pkg=String.valueOf(root.getPackageName()).toLowerCase(Locale.US);if(!pkg.contains("hardrock")&&!pkg.contains("seminole")){stopScan();return;}List<String> lines=new ArrayList<>();collect(root,lines);EventStore.ingest(lines);String snapshot=lines.toString();unchanged=snapshot.equals(previous)?unchanged+1:0;previous=snapshot;if(unchanged>=4||steps++>=250){stopScan();return;}AccessibilityNodeInfo sc=scrollable(root);if(sc!=null&&sc.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)){handler.postDelayed(()->tick(),1000);return;}android.util.DisplayMetrics m=getResources().getDisplayMetrics();Path path=new Path();path.moveTo(m.widthPixels*.5f,m.heightPixels*.79f);path.lineTo(m.widthPixels*.5f,m.heightPixels*.27f);GestureDescription gesture=new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path,0,550)).build();dispatchGesture(gesture,new GestureResultCallback(){@Override public void onCompleted(GestureDescription g){handler.postDelayed(()->tick(),1000);}@Override public void onCancelled(GestureDescription g){stopScan();}},null);}
}
