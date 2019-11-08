package com.dulupa.commentanaylizer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dulupa.commentanaylizer.R;
import com.dulupa.commentanaylizer.struct.Comment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    ArrayList<Comment> comments;
    Context context;

    public CommentAdapter( Context context,ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Comment comment=comments.get(position);
            holder.author.setText(comment.getAuthor());
            holder.content.setText(comment.getContent());
            holder.publishDate.setText(comment.getPublish_data());
            holder.likes.setText(comment.getLike_count()+"");
            holder.replies.setText(comment.getReply_count()+"");
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    //包裹Item view結構，方便設定子view
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView content;
        TextView publishDate;
        TextView likes;
        TextView replies;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.tv_author);
            content=itemView.findViewById(R.id.tv_content);
            publishDate=itemView.findViewById(R.id.tv_publish_date);
            likes=itemView.findViewById(R.id.tv_like);
            replies=itemView.findViewById(R.id.tv_reply);
        }
    }
}
