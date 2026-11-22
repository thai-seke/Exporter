package com.hardrock.eventexporter;
import java.util.*;
import java.util.regex.*;
import java.text.*;
public class EventStore {
 public static class Item { public String title,date,time; Item(String a,String b,String c){title=a;date=b;time=c;} }
 public static final LinkedHashMap<String,Item> items=new LinkedHashMap<>();
 private static final Pattern DATE=Pattern.compile("(?i)(?:mon|tue|wed|thu|fri|sat|sun)[a-z]*,?\\s+)?(?:jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\\.?\\s+\\d{1,2}(?:,?\\s+20\\d{2})?");
 private static final Pattern TIME=Pattern.compile("(?i)\\b\\d{1,2}(?::\\d{2})?\\s*(?:am|pm)(?:\\s*[-–]\\s*\\d{1,2}(?::\\d{2})?\\s*(?:am|pm))?");
 public static synchronized void ingest(List<String> lines){ List<String> clean=new ArrayList<>(new LinkedHashSet<>(lines)); for(int i=0;i<clean.size();i++){ Matcher dm=DATE.matcher(clean.get(i)); if(!dm.find())continue; String date=dm.group(),title=null,time=""; for(int j=Math.max(0,i-4);j<Math.min(clean.size(),i+6);j++){String s=clean.get(j).trim(); Matcher tm=TIME.matcher(s);if(tm.find()&&time.isEmpty())time=tm.group(); if(j<i && s.length()>=5 && s.length()<=110 && !DATE.matcher(s).find()&&!TIME.matcher(s).find()&&!s.matches("(?i)(reserved|offers|my offers|details|view|back|home|cancel|claim|redeem)"))title=s; } if(title!=null)items.put((title+"|"+date+"|"+time).toLowerCase(Locale.US),new Item(title,date,time)); } }
 private static String quote(String s){return "\""+s.replace("\"","\"\"")+"\"";}
 public static synchronized String csv(){StringBuilder b=new StringBuilder("Event,Date,Time,Status\r\n");for(Item i:items.values())b.append(quote(i.title)).append(',').append(quote(i.date)).append(',').append(quote(i.time)).append(",Reserved\r\n");return b.toString();}
 private static String esc(String s){return s.replace("\\","\\\\").replace(";","\\;").replace(",","\\,").replace("\n","\\n");}
 public static synchronized String ics(){StringBuilder b=new StringBuilder("BEGIN:VCALENDAR\r\nVERSION:2.0\r\nPRODID:-//HardRockEventExporter//EN\r\n");SimpleDateFormat input=new SimpleDateFormat("MMM d yyyy",Locale.US),output=new SimpleDateFormat("yyyyMMdd",Locale.US),stamp=new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'",Locale.US);input.setLenient(false);stamp.setTimeZone(TimeZone.getTimeZone("UTC"));int n=0;for(Item i:items.values()){try{String d=i.date.replace(",","").trim();if(!d.matches(".*20\\d{2}.*"))d+=" 2026";String day=output.format(input.parse(d));b.append("BEGIN:VEVENT\r\nUID:hr-").append(n++).append('-').append(day).append("@local\r\nDTSTAMP:").append(stamp.format(new Date())).append("\r\nDTSTART;VALUE=DATE:").append(day).append("\r\nSUMMARY:").append(esc(i.title)).append("\r\nDESCRIPTION:").append(esc("Reserved. Displayed time: "+i.time)).append("\r\nEND:VEVENT\r\n");}catch(Exception ignored){}}return b.append("END:VCALENDAR\r\n").toString();}
}
