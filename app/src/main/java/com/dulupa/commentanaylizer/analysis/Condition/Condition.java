package com.dulupa.commentanaylizer.analysis.Condition;

import com.dulupa.commentanaylizer.struct.Comment;

public interface Condition {
    boolean isFullfilledBy(Comment comment);
}
