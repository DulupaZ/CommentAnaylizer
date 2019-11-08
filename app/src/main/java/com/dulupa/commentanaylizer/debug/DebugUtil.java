package com.dulupa.commentanaylizer.debug;

import android.util.Log;

import com.dulupa.commentanaylizer.converter.Resolver;
import com.dulupa.commentanaylizer.struct.Comment;

import java.util.ArrayList;

public class DebugUtil {

    //列出comments
    public static void showComments(ArrayList<Comment> comments){
        for(Comment comment:comments){
            Log.d("debug","id\t"+comment.getId());
            Log.d("debug","Author\t"+comment.getAuthor());
            Log.d("debug","IMG\t"+comment.getAuthor_img_url());
            Log.d("debug","Content\t"+comment.getContent());
            Log.d("debug","likes\t"+comment.getLike_count());
            Log.d("debug","replies\t"+comment.getReply_count());
            Log.d("debug","Publish date\t"+comment.getPublish_data());
        }
    }

    //列出過濾後的comments
    public static void showFilteredComments(ArrayList<Comment> origin,ArrayList<Comment> filtered){
        Log.d("debug","----------Start FilteredComments------------");
        Log.d("debug",origin.size()+" origin");
        Log.d("debug",filtered.size()+" filtered");
        showComments(filtered);
        Log.d("debug","----------End FilteredComments------------");
    }
}
