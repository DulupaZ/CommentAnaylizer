package com.dulupa.commentanaylizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dulupa.commentanaylizer.adapter.CommentAdapter;
import com.dulupa.commentanaylizer.analysis.Filter;
import com.dulupa.commentanaylizer.converter.DateUtil;
import com.dulupa.commentanaylizer.converter.Resolver;
import com.dulupa.commentanaylizer.debug.DebugUtil;
import com.dulupa.commentanaylizer.struct.Comment;
import com.dulupa.commentanaylizer.task.QueryCommentLoader;
import com.dulupa.commentanaylizer.task.QueryCommentTask;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    static final int LOAD_EACH_TIME=3;

    /**********************UI  View*********************************************/
    Button btn_next;

    RecyclerView rv_comment;
        CommentAdapter commentAdapter;
        ArrayList<Comment> comments;

    Button btn_test;

    /**********************Loader Framework*********************************************/
    LoaderManager loaderManager;
    //Loaders ID
    static final int LOADER_COMMENT_THREAD_LIST=1;
        static final String KEY_VIDEO_ID=BuildConfig.APPLICATION_ID+"KEY_VIDEO_ID";
        static final String KEY_MAX_RESULT=BuildConfig.APPLICATION_ID+"KEY_MAX_RESULT";


    /**********************Instance_State********************************************/
    static final String INSTANCE_STATE_COMMENTS=BuildConfig.APPLICATION_ID+"INSTANCE_STATE_COMMENTS";
    static final String INSTANCE_STATE_NEXT_PAGE_TOKEN=BuildConfig.APPLICATION_ID+"INSTANCE_NEXT_PAGE_TOKEN";

    //boolean isRebindLoader=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData(savedInstanceState);
        bindViewById();
        bindDataToAdapterView();
        reBindLoader();



        //讀取更多
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle loaderArgs=new Bundle();
                    loaderArgs.putString(KEY_MAX_RESULT,String.valueOf(LOAD_EACH_TIME));
                    loaderArgs.putString(KEY_VIDEO_ID,"6kZg09ic9oQ");
                //isRebindLoader=false;
                loaderManager.restartLoader(LOADER_COMMENT_THREAD_LIST,
                                            loaderArgs,
                                            new LoadCommentThreadListHandler()).forceLoad();
            }
        });


        //按鈕觸發debug method (把當前的comment list篩選在印出來)
        btn_test=findViewById(R.id.button);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter filter=new Filter();
                ArrayList<Comment> filtered=filter.filterByCodition(comments);
                DebugUtil.showFilteredComments(comments,filtered);
            }
        });

    }

    //把讀取過的comment保存下來
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INSTANCE_STATE_COMMENTS,comments);
        Log.d("debug","onSaveInstanceState()");
        outState.putString(INSTANCE_STATE_NEXT_PAGE_TOKEN,Resolver.getInstance().getNextPageToken());
    }

    //用async task讀取comments
    void queryComments(String videoId,int maxResult){
        new QueryCommentTask(   Resolver.getInstance().getNextPageToken(),
                                String.valueOf(maxResult),
                                videoId,
                                comments,
                                commentAdapter).execute();
    }
    void bindViewById(){
        btn_next=findViewById(R.id.btn_next);
        rv_comment=findViewById(R.id.rv_comments);
    }
    //如果有上一次的紀錄，就抓，沒有，就開空的
    void initData(Bundle instanceState){
        if(instanceState==null)
            comments=new ArrayList<>();
        else {
            comments = instanceState.getParcelableArrayList(INSTANCE_STATE_COMMENTS);
            //Resolver.getInstance().setNextPageToken(instanceState.getString(INSTANCE_STATE_NEXT_PAGE_TOKEN));
        }
    }
    void bindDataToAdapterView(){
        commentAdapter=new CommentAdapter(this,comments);
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(commentAdapter);
    }
    //重新綁定loader和activity
    void  reBindLoader(){

        loaderManager=LoaderManager.getInstance(this);
        //如果背景任務存在會綁回去(忽略bundle)，不存在就算了(因為一開始不用讀)
        if((loaderManager.getLoader(LOADER_COMMENT_THREAD_LIST)!=null)){
            Log.d("debug","reBindLoader()");
            //isRebindLoader=true;
            loaderManager.initLoader(   LOADER_COMMENT_THREAD_LIST,
                    null,
                    new LoadCommentThreadListHandler());
        }
    }
    //讀取CommentThreadList的Loader狀態處理回調實例(開啟任務/完成更新Ui)
    class LoadCommentThreadListHandler implements LoaderManager.LoaderCallbacks<String>{
        @NonNull
        @Override
        //開始任務，去讀取comment thread list raw json
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            Log.d("debug","onCreateLoader");
            return new QueryCommentLoader(  MainActivity.this,
                                            args.getString(KEY_VIDEO_ID),
                                            args.getString(KEY_MAX_RESULT),
                                            Resolver.getInstance().getNextPageToken());
        }
        //讀取結束，做成comment加入all comment，再通知adapter更新recy view
        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String rawJson) {
            //if(!isRebindLoader) {
                Log.d("debug", " onLoadFinished");
                comments.addAll(Resolver.getInstance().resolveCommentsFrom(rawJson));
                commentAdapter.notifyDataSetChanged();
            //}
        }
        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    }

}
