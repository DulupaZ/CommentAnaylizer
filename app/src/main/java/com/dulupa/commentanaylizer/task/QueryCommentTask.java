package com.dulupa.commentanaylizer.task;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.dulupa.commentanaylizer.adapter.CommentAdapter;
import com.dulupa.commentanaylizer.converter.Resolver;
import com.dulupa.commentanaylizer.struct.Comment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class QueryCommentTask extends AsyncTask<Void,Integer,String>{

    private static final String COMMENT_BASE_URL =  "https://www.googleapis.com/youtube/v3/commentThreads?";

    private static final String QUERY_PART = "part";
        private static final String VALUE_PART = "id,snippet";
    private static final String QUERY_MAX_RESULTS = "maxResults";

    private static final String QUERY_ORDER = "order";
        private static final String VALUE_ORDER_RELEVANCE= "relevance";

    private static final String QUERY_VIDEO_ID= "videoId";

    private static final String QUERY_API_KEY= "key";
        private static final String VALUE_API_KEY= "AIzaSyAQTMo7X1ZuSDjl1xd-37W-RUItUTjVlUM";
    private  static final String QUERY_PAGE_TOKEN="pageToken";


    String query_maxresult;
    String query_videoid;
    String nextPageToken="";

    ArrayList<Comment> comments;
    CommentAdapter commentAdapter;

    public QueryCommentTask(String nextPageToken,String query_maxresult, String query_videoid, ArrayList<Comment> comments,CommentAdapter commentAdapter) {
        this.nextPageToken=nextPageToken;
        this.query_maxresult = query_maxresult;
        this.query_videoid = query_videoid;
        this.comments = comments;
        this.commentAdapter=commentAdapter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        //result
        StringBuilder rawJson=new StringBuilder();

        //先打造符合google book規範的 get請求(已取得包含查詢到的書本的response)
        //用uri便是位置
        Uri request_uri= Uri.parse(COMMENT_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PART,VALUE_PART )
                        .appendQueryParameter(QUERY_MAX_RESULTS,query_maxresult)
                        .appendQueryParameter(QUERY_ORDER,VALUE_ORDER_RELEVANCE)
                        .appendQueryParameter(QUERY_PAGE_TOKEN,nextPageToken)
                        .appendQueryParameter(QUERY_VIDEO_ID,query_videoid)
                        .appendQueryParameter(QUERY_API_KEY,VALUE_API_KEY)
                        .build();
        Log.d("debug", request_uri.toString());


        //在做成url存取資源
        try {
            URL request= new URL(request_uri.toString());
            HttpURLConnection connection=(HttpURLConnection)request.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(
                                    connection.getInputStream()));

            String line;
            while( (line=reader.readLine())!=null){
                rawJson.append(line);
                rawJson.append('\n');
            }
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawJson.toString();
    }

    @Override
    protected void onPostExecute(String rawJson) {
        comments.addAll(Resolver.getInstance().resolveCommentsFrom(rawJson));
        commentAdapter.notifyDataSetChanged();
    }
}
