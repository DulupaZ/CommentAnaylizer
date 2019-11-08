package com.dulupa.commentanaylizer.analysis.Condition;

import com.dulupa.commentanaylizer.converter.DateUtil;
import com.dulupa.commentanaylizer.struct.Comment;

import java.util.Calendar;

public class DateBetweenCondition implements Condition{

    private String date_start;
    private String date_end;

    public DateBetweenCondition(String date_start, String date_end) {
        this.date_start = date_start;
        this.date_end = date_end;
    }

    //如果發布日期在起始到結束之間，回傳true
    //bug連時間都會去比較母湯喔
    @Override
    public boolean isFullfilledBy(Comment comment) {
        String publish=comment.getPublish_data();
        //發布在start之後，end之前
        return DateUtil.dateAfter(publish,date_start)&&DateUtil.dateBefore(publish,date_end);
    }
}
