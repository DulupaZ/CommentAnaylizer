package com.dulupa.commentanaylizer.converter;


import org.apache.commons.text.StringEscapeUtils;

public class HtmlParcer {

    static final String newLine="<br />";

    public static String parce(String raw){
        //先把br改成換行，再把其他html tag清除，再次解跳脫 &#UTF-8編碼
      return StringEscapeUtils.unescapeHtml4(raw.replace(newLine,"\n").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));

    }
}
