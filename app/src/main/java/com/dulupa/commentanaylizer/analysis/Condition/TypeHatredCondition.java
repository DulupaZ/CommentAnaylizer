package com.dulupa.commentanaylizer.analysis.Condition;

import com.dulupa.commentanaylizer.struct.Comment;

public class TypeHatredCondition implements Condition {
    final static int Threshold_Reply_Min=3;
    final static int Threshold_Like_MAX=5;
    @Override
    public boolean isFullfilledBy(Comment comment) {
        return comment.getLike_count()<Threshold_Like_MAX&&comment.getReply_count()>Threshold_Reply_Min;
    }
}
