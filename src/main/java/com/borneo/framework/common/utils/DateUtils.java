package com.borneo.framework.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.borneo.framework.base.vo.LabelValueVO;

/**
 * @author peter.yuan
 */
public class DateUtils {

    public static final String FULL_FORMAT_ZZZ = "E, dd MMM yyyy HH:mm:ss zzz";
    public static final String FULL_FORMATONE = "yyyy/MM/dd HH:mm:ss zzz";
    public static final String FULL_FORMATTWO = "yyyy-MM-dd HH:mm:ss zzz";//2011-10-20 02:43:57 GMT
    public static final String SHORT_FORMAT = "yyyy-MM-dd";
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat DF = new SimpleDateFormat(SHORT_FORMAT);
    public static final SimpleDateFormat DF_TM = new SimpleDateFormat(FULL_FORMAT);
    public static final SimpleDateFormat DF_6 = new SimpleDateFormat("yyMMdd");
    public static final SimpleDateFormat DF_8 = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat DF_15 = new SimpleDateFormat("yyMMddHHmmssSSS");
    public static final SimpleDateFormat DF_YM = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat DF_WEEK = new SimpleDateFormat("E");
    public static final SimpleDateFormat DF_12 = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    public static Map<String, String> map = new HashMap<String, String>();
    static {
        map.put("####-##-##", "yyyy-MM-dd");// length=10
        map.put("####-##", "yyyy-MM");// length=7
        map.put("######", "yyyyMM");// 6
        map.put("####-##-## ##:##:##", "yyyy-MM-dd HH:mm:ss");// 19
    }

    public static String getDateByLong(long miliseconds) {
        return new SimpleDateFormat(FULL_FORMAT_ZZZ).format(miliseconds);
    }
    
    public static String getTodayZhCn() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy年MM月dd日E").format(calendar.getTime());
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getYestodayShort() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        return DF.format(calendar.getTime());
    }

    public static String getYestodayFull() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        return DF_TM.format(calendar.getTime());
    }

    public static String getTomorrowShort() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return DF.format(calendar.getTime());
    }

    public static String getTomorrowFull() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return DF_TM.format(calendar.getTime());
    }

    public static String getTodayShort() {
        Calendar calendar = Calendar.getInstance();
        return DF.format(calendar.getTime());
    }

    public static String getTodayFull() {
        Calendar calendar = Calendar.getInstance();
        return DF_TM.format(calendar.getTime());
    }

    public static String getDateFormat(String strDate, String inFmt, String outFmt) throws Exception {
        SimpleDateFormat inDateFormat = new SimpleDateFormat(inFmt);
        inDateFormat.setLenient(true);
        Date inDate = inDateFormat.parse(strDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat(outFmt);
        return dateFormat.format(inDate);
    }

    public static String timestamp2Str(Timestamp time, String timeFormat) {
        if ((time == null) || (time.getTime() == 0)) {
            return "";
        }
        Date d = new Date(time.getTime());
        if (StringUtils.isEmpty(timeFormat)) {
            return DF.format(d);
        } else {
            return new SimpleDateFormat(timeFormat).format(d);
        }
    }

    public static String timestamp2FullStr(Timestamp time) {
        return timestamp2Str(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToStr(Date dteInput, String dateFormat) {
        try {
            if (dteInput == null) {
                return "";
            } else {
                SimpleDateFormat simpledateformat = new SimpleDateFormat(dateFormat);
                simpledateformat.setTimeZone(TimeZone.getDefault());
                return simpledateformat.format(dteInput);
            }
        } catch (Exception e) {
            return dteInput.toString();
        }
    }

    public static String dateToShortStr(Date dteInput) {
        return dateToStr(dteInput, "yyyy-MM-dd");
    }

    public static String dateToFullStr(Date dteInput) {
        return dateToStr(dteInput, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDateByAdd(Date dteInput, int intDay) {
        try {
            Date dteTemp = new Date();
            dteTemp.setTime(dteInput.getTime() + (0x5265c00L * intDay));
            return dteTemp;
        } catch (Exception e) {
            return dteInput;
        }
    }

    public static Date getDateByMinus(Date dteInput, int intDay) {
        try {
            Date dteTemp = new Date();
            dteTemp.setTime(dteInput.getTime() - (0x5265c00L * intDay));
            return dteTemp;
        } catch (Exception e) {
            return dteInput;
        }
    }

    public static int getCurrentTimeProperty(int property) {
        Calendar ca = Calendar.getInstance();
        if (Calendar.MONTH == property) {
            return ca.get(property) + 1;
        } else {
            return ca.get(property);
        }
    }

    public static Date getBeginDateByStr(String dateStr, String dateFormat) {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = SHORT_FORMAT;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(string2Date(dateStr, dateFormat).getTime());
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    public static Date getEndDateByStr(String dateStr, String dateFormat) {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = SHORT_FORMAT;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(string2Date(dateStr, dateFormat).getTime());
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 999);
        return ca.getTime();
    }

    public static Timestamp getBeginTimeByTimeStamp(Timestamp timestamp) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timestamp.getTime());
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return new Timestamp(ca.getTime().getTime());
    }

    public static Timestamp getEndTimeByTimeStamp(Timestamp timestamp) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timestamp.getTime());
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 999);
        return new Timestamp(ca.getTime().getTime());
    }

    public static Timestamp getBeginTimeByStr(String dateStr, String dateFormat) {
        Timestamp timestamp = string2Timestamp(dateStr, dateFormat);
        return getBeginTimeByTimeStamp(timestamp);
    }

    public static Timestamp getEndTimeByStr(String dateStr, String dateFormat) {
        Timestamp timestamp = string2Timestamp(dateStr, dateFormat);
        return getEndTimeByTimeStamp(timestamp);
    }

    public static Date getTimeByTimeStamp(Timestamp timestamp, int hour, int min) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timestamp.getTime());
        ca.set(Calendar.HOUR_OF_DAY, hour);
        ca.set(Calendar.MINUTE, min);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    public static Timestamp getBeginTimeByYMD(String strDate) {
        String[] strDateSplit = strDate.split("-");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, Integer.valueOf(strDateSplit[0]));
        ca.set(Calendar.MONTH, Integer.valueOf(strDateSplit[1]) - 1);
        ca.set(Calendar.DAY_OF_MONTH, Integer.valueOf(strDateSplit[2]));
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return new Timestamp(ca.getTime().getTime());
    }

    public static Timestamp getEndTimeByYMD(String strDate) {
        String[] strDateSplit = strDate.split("-");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, Integer.valueOf(strDateSplit[0]));
        ca.set(Calendar.MONTH, Integer.valueOf(strDateSplit[1]) - 1);
        ca.set(Calendar.DAY_OF_MONTH, Integer.valueOf(strDateSplit[2]));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 999);
        return new Timestamp(ca.getTime().getTime());
    }

    public static Date string2Date(String dateStr, String dateFormat) {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = SHORT_FORMAT;
        }
        Date date;
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public static Date shortStr2Date(String dateStr) {
        return string2Date(dateStr, null);
    }

    public static Date fullStr2Date(String dateStr) {
        return string2Date(dateStr, FULL_FORMAT);
    }

    public static Timestamp string2Timestamp(String dateStr, String dateFormat) {
        Date date = string2Date(dateStr, dateFormat);
        return new Timestamp(date.getTime());
    }

    public static Date getBeginTimeOfMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date.getTime());
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    public static Date getEndTimeOfMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date.getTime());
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.MILLISECOND, -1);
        return ca.getTime();
    }

    public static List<Integer> getYearList() {
        Calendar ca = Calendar.getInstance();
        List<Integer> yearList = new ArrayList<Integer>();
        yearList.add(ca.get(Calendar.YEAR));
        yearList.add(ca.get(Calendar.YEAR) - 1);

        return yearList;
    }

    public static Date toStartOfMonthYear(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date toEndOfMonthYear(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    /* for member overriding level */
    public static Date getStartDate(int noOfMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -noOfMonth);

        return c.getTime();
    }

    public static String getTimestampInString() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.getTimeInMillis()); //return new SimpleDateFormat("yyyy年MM月dd日E").format(calendar.getTime());
    }
    
    //Added by Jacky 06-Aug-14 1636
    public static Date formatDateByTime(Date date, int hour, int minute, int second, int milisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, milisecond);
		
		return calendar.getTime();
	}
    
  //Added by Jacky 06-Aug-14 1636
    public static Date getNextDateTimeByMinuteDrive(Date currentSystemDate,Integer min){
		
		Date newCurrentDate = currentSystemDate;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(newCurrentDate);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+min);
		Date newDate = cal.getTime();
		return newDate;
	}
    
    //Added by Jacky 12-Aug-14 1340
    public static Date getNextDateTimeByHourDrive(Date currentSystemDate,Integer hr){
		
		Date newCurrentDate = currentSystemDate;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(newCurrentDate);
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+hr);
		Date newDate = cal.getTime();
		return newDate;
	}
    
    //Added by Jacky 25-Sept-14 1457
    public static Date getNextDateTimeByDayDrive(Date currentSystemDate,Integer day){
		
		Date newCurrentDate = currentSystemDate;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(newCurrentDate);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)+day);
		Date newDate = cal.getTime();
		return newDate;
	}
    
  //Added by Jacky 06-Aug-14 1636
    public static List<LabelValueVO> getSelectTimes(int timeGap){
    	Date startTime = formatDateByTime(new Date(),0,0,0,0);
    	Date endTime = formatDateByTime(new Date(),23,30,59,59);
    	
    	boolean isRun=true;
    	boolean isFirst=false;
    	List<LabelValueVO> strTimes=new ArrayList<LabelValueVO>();
    	Date chkDate=null;
    	String value;
    	while (isRun) {
    		if (chkDate==null) chkDate = startTime;
    		if (!isFirst){
    			isFirst=true;
    			value = dateToStr(chkDate, "HH:mm");
    			LabelValueVO labelValueVO=new LabelValueVO(value, value);
    			strTimes.add(labelValueVO);
    		}else{
    			chkDate = getNextDateTimeByMinuteDrive(chkDate,timeGap);
    			
    			if (chkDate.after(endTime)) {
    				isRun=false;
    			}else{
    				value = dateToStr(chkDate, "HH:mm");
    				LabelValueVO labelValueVO=new LabelValueVO(value, value);
    				strTimes.add(labelValueVO);	
    			}
    		}
		}
    	return strTimes;
    }
    
    //Added by Jacky 12-Aug-14 1333
    public static List<LabelValueVO> getNextSelectDateTimesWithTZ(int firstCollHour, int subSeqCollInMin, Date startTime, Date endTime){
    	boolean isRun=true;
    	boolean isFirst=false;
    	List<LabelValueVO> strTimes=new ArrayList<LabelValueVO>();
    	Date chkDate=null;
    	String value;
    	while (isRun) {
    		if (chkDate==null) chkDate = getNextDateTimeByHourDrive(startTime,firstCollHour);
    		
    		if (chkDate.after(endTime)) {
    			isRun=false;
    		}
    		if (!isFirst){
    			isFirst=true;
    			value = dateToStr(chkDate, "yyyy-MM-dd HH:mm Z");
    			LabelValueVO labelValueVO=new LabelValueVO(value, value);
    			strTimes.add(labelValueVO);
    		}else{
    			chkDate = getNextDateTimeByMinuteDrive(chkDate,subSeqCollInMin);
    			
    			if (chkDate.before(endTime)) {
    				value = dateToStr(chkDate, "yyyy-MM-dd HH:mm Z");
    				LabelValueVO labelValueVO=new LabelValueVO(value, value);
    				strTimes.add(labelValueVO);	
    			}
    		}
		}
    	return strTimes;
    }
    
    //Added by Jacky 12-Aug-14 1333
    public static Integer getDayOfWeekIdx(Date todayDate){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(todayDate);
    	return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    public static Integer getHour(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	return cal.get(Calendar.HOUR_OF_DAY);
    }
    
    public static Integer getMinute(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.MINUTE);
    }
    
    public static Integer getSecond(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.SECOND);
    }
    
    public static void main(String[] args) {
    	String dateTZ = dateToStr(new Date(), "yyyy-MM-dd HH:mm Z");
    	System.out.println(dateTZ);
    }
}
