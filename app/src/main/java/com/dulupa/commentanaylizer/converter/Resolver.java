package com.dulupa.commentanaylizer.converter;


import android.util.Log;
import com.dulupa.commentanaylizer.struct.Comment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class Resolver {


    private static Resolver instance;



    private String nextPageToken="";

    public static Resolver getInstance(){
        if(instance==null){
            instance=new Resolver();
        }
        return instance;
    }


   public ArrayList<Comment> resolveCommentsFrom(String rawJson){
        ArrayList<Comment> result=new ArrayList<>();

       try {
           JSONObject whole=new JSONObject(rawJson);
           //更新下一次讀取的起點
           nextPageToken=whole.optString("nextPageToken",null);
           //所有comment thread
           JSONArray items=whole.optJSONArray("items");
                for(int i=0;i<items.length();i++){
                    //某個comment Thread
                    JSONObject commentthread=items.getJSONObject(i);
                        //某個comment Thread的snippet
                        JSONObject container=commentthread.getJSONObject("snippet");
                            //某個comment Thread的snippet的topLevelComment(最頂端的留言Comment)
                            JSONObject itself=container.getJSONObject("topLevelComment");
                                //id
                                String id=itself.optString("id",null);
                                //snippet
                                JSONObject snippet=itself.getJSONObject("snippet");
                                    String author=snippet.optString("authorDisplayName","Unknown");
                                    String author_img_url=snippet.optString( "authorProfileImageUrl",null);
                                    String content=snippet.optString("textDisplay",null);
                                    int like_count=snippet.optInt("likeCount",0);
                                    String publish_date=snippet.optString("publishedAt",null);
                            //某個comment Thread的snippet的totalReplyCount(最頂端留言的回復數)
                            int totalReplyCount=container.optInt("totalReplyCount",0);
                        //輸入所有欄位造出comment
                        result.add(new Comment(author,author_img_url,content,like_count,publish_date,id,totalReplyCount));


                }
       } catch (JSONException e) {
            Log.d("debug","JSON Ex"+e.getMessage());
       }


       return result;
    }
    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }


    public  void listRawJson(String rawJson){
        try {
            JSONObject whole=new JSONObject(rawJson);

                JSONArray items=whole.getJSONArray("items");
                     for(int i=0;i<items.length();i++){
                         Log.d("debug","第"+(i+1)+"本書");
                         JSONObject book=items.getJSONObject(i);
                                traversalJSONObject(book.getJSONObject("volumeInfo").getJSONObject("imageLinks"));
                     }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

     void traversalJSONObject(JSONObject jsonObject){
        for(Iterator<String> itr = jsonObject.keys();itr.hasNext();) {
            String fieldName=itr.next();
            try {
                Log.d("debug",fieldName+":"+jsonObject.get(fieldName));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    void traversalJSONArray(JSONArray jsonArray){
            for(int i=0;i<jsonArray.length();i++){
                try {
                    Log.d("debug",jsonArray.get(i).toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
    }

    public String getNextPageToken() {
        Log.d("debug","nextPageToken="+nextPageToken);
        return nextPageToken;
    }


}
