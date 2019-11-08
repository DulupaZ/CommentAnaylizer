package com.dulupa.commentanaylizer.converter;

import java.util.Calendar;

public class DateUtil {
    //Time Format
    //2019-11-06T07:31:55.000Z

    //把時間格式字串轉成Calendar物件
    public static Calendar from(String dateFormatString){
       Calendar.Builder builder=new Calendar.Builder();
       //日期
           //欄位由string-->int
           String[] Y_M_D_str=dateFormatString.substring(0,10).split("-");
           int[] Y_M_D=new int[3];
           for(int i=0;i<Y_M_D.length;i++){
               Y_M_D[i]=Integer.parseInt(Y_M_D_str[i]);
           }
           //透過設定calendar
           builder.setDate(Y_M_D[0],Y_M_D[1],Y_M_D[2]);
    //時間
       String[] h_m_s_str=dateFormatString.substring(   dateFormatString.indexOf('T')+1,
                                                        dateFormatString.indexOf("."))
                                            .split(":");
       int[] h_m_s=new int[3];
       for(int i=0;i<h_m_s_str.length;i++){
           h_m_s[i]=Integer.parseInt(h_m_s_str[i]);
       }
       builder.setTimeOfDay(h_m_s[0],h_m_s[1],h_m_s[2]);

       return builder.build();
   }

    //(只比較日期)A晚於等於B回傳True
    public static boolean dateAfter(String a,String b){
        //把年月日存在0,1,2元素
        String[] a_YEAR_MONTH_DAY=     a.substring(0,10).split("-");
        String[] b_YEAR_MONTH_DAY=    b.substring(0,10).split("-");
        //掃過選擇的年月日
        for(int i=0;i<a_YEAR_MONTH_DAY.length;i++){
            int a_ymd=Integer.parseInt(a_YEAR_MONTH_DAY[i]);
            int  b_ymd=Integer.parseInt(b_YEAR_MONTH_DAY[i]);
            //如果留言的日其  早過  選擇的日期
            if((a_ymd>b_ymd))
                return true;
                //如果留言的日其  晚過  選擇的日期
            else if(a_ymd<b_ymd)
                return false;
            //如果留言的年or月or日  一樣  選擇的日期，就去比下一個欄位
        }
        //年月日完全相同那就當作滿足
        return true;
    }

    //(只比較日期)A晚於等於B回傳True
    public static boolean dateBefore(String a,String b){
        //把年月日存在0,1,2元素
        String[] a_YEAR_MONTH_DAY=     a.substring(0,10).split("-");
        String[] b_YEAR_MONTH_DAY=    b.substring(0,10).split("-");
        //掃過選擇的年月日
        for(int i=0;i<a_YEAR_MONTH_DAY.length;i++){
            int a_ymd=Integer.parseInt(a_YEAR_MONTH_DAY[i]);
            int  b_ymd=Integer.parseInt(b_YEAR_MONTH_DAY[i]);
            //如果留言的日其  早過  選擇的日期
            if((a_ymd<b_ymd))
                return true;
                //如果留言的日其  晚過  選擇的日期
            else if(a_ymd>b_ymd)
                return false;
            //如果留言的年or月or日  一樣  選擇的日期，就去比下一個欄位
        }
        //年月日完全相同那就當作滿足
        return true;
    }
   //test code
   /*
       Calendar calendar= DateUtil.from("2019-11-05T14:16:22.000Z");
        Log.d("debug",   calendar.get(Calendar.YEAR)+"/"
                        +calendar.get(Calendar.MONTH)+"/"
                        +calendar.get(Calendar.DAY_OF_MONTH)+"/"
                        +calendar.get(Calendar.HOUR_OF_DAY)+"/"
                        +calendar.get(Calendar.MINUTE)+"/"
                        +calendar.get(Calendar.SECOND)
        );
   */
   /*test code
     //2019-11-06T07:31:55.000Z
        Log.d("debug",DateUtil.dateAfter("2019-11-06T07:31:55.000Z","2019-11-07T07:31:55.000Z")+"2019-11-06晚於等於2019-11-07");
        Log.d("debug",DateUtil.dateAfter("2019-11-07T07:31:55.000Z","2019-11-06T07:31:55.000Z")+"2019-11-07晚於等於2019-11-06");
        Log.d("debug",DateUtil.dateAfter("2019-11-07T07:31:55.000Z","2019-11-07T07:31:55.000Z")+"2019-11-07晚於等於2019-11-07");
    */
}
