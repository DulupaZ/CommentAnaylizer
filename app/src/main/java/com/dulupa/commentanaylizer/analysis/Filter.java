package com.dulupa.commentanaylizer.analysis;

import android.app.DatePickerDialog;

import com.dulupa.commentanaylizer.analysis.Condition.Condition;
import com.dulupa.commentanaylizer.analysis.Condition.DateAfterCondition;
import com.dulupa.commentanaylizer.analysis.Condition.DateBeforeCondition;
import com.dulupa.commentanaylizer.analysis.Condition.DateBetweenCondition;
import com.dulupa.commentanaylizer.analysis.Condition.TypeHotCondition;
import com.dulupa.commentanaylizer.struct.Comment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Filter {
    ArrayList <Condition> allConditions;

    public Filter(){
        allConditions=new ArrayList<>();
        //測試，之後是透過setteer改變filter的篩選條件s
       // allConditions.add(new TypeHotCondition()) ;
        allConditions.add(new DateBetweenCondition("2019-11-04T06:00:00.000Z","2019-11-05T18:00:00.000Z"));


    }

    public ArrayList<Comment> filterByCodition(ArrayList<Comment> comments){
        ArrayList<Comment> result=new ArrayList<>();
        //根據條件過濾comment
        for(Comment comment:comments){
            //如果comment滿足所有設定的條件，就家道結果
            if(isFullFillCondition(comment))
                result.add(comment);
        }

        return result;
    }

    //setXXXCondition
    boolean isFullFillCondition(Comment comment){
        for(Condition condition:allConditions){
            if(!condition.isFullfilledBy(comment))
                return false;
        }
        return true;
    }
}
