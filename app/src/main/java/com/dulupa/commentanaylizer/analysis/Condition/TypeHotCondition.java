package com.dulupa.commentanaylizer.analysis.Condition;

import com.dulupa.commentanaylizer.struct.Comment;

public class TypeHotCondition implements Condition {

    //如果      回復大於Threshold_Reply_MIN
    //          喜歡超過ThreadsHolde_Like_MIN
    // 就是熱門
    final static int Threshold_Reply_MIN=10;
    final static int ThreadsHolde_Like_MIN=10;
    @Override
    public boolean isFullfilledBy(Comment comment) {
        return (comment.getReply_count() > 10 && comment.getLike_count() > 10);
    }
}
