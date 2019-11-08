package com.dulupa.commentanaylizer.analysis.Condition;

import com.dulupa.commentanaylizer.converter.DateUtil;
import com.dulupa.commentanaylizer.struct.Comment;

public class DateBeforeCondition implements Condition {

    //日期格式
    //2019-11-06T07:31:55.000Z


    //選擇的日期
    private String date;

    public DateBeforeCondition(String date){
        this.date=date;
    }

    //如果流言日期早於等於選擇日期，回傳True
    @Override
    public boolean isFullfilledBy(Comment comment) {
        return DateUtil.dateBefore(comment.getPublish_data(),date);
    }


}
