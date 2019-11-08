package com.dulupa.commentanaylizer.analysis.Condition;

import com.dulupa.commentanaylizer.converter.DateUtil;
import com.dulupa.commentanaylizer.struct.Comment;

public class DateAfterCondition implements Condition {
    //選擇的日期
    private String date;

    public DateAfterCondition(String date){
        this.date=date;
    }

    //如果流言日期晚於等於選擇日期，回傳True
    @Override
    public boolean isFullfilledBy(Comment comment) {
        return DateUtil.dateAfter(comment.getPublish_data(),date);
    }
}
