package com.trs.aiweishi.util;

import java.util.regex.Pattern;

/**
 * Created by wuwei on 2017/9/20.
 * description : 正则替换标签
 */
public class RegularConversionUtil {

    public static String removeHtmlTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_font;
        java.util.regex.Matcher m_font;
        Pattern p_width;
        java.util.regex.Matcher m_width;
        Pattern pImg_height;
        java.util.regex.Matcher mImg_height;

        try {
            String regEx_style = "style=\\\"(.*?)\\\"";
            String regEx_font = "</?font[^><]*>";
            String regEx_width = "width[\\s]*?=[\\\\]?[\\s]*?[\\\"|'][^\\\"|^')]+[\\\"|'][\\\\]?";
            String regExImg_height = "height=\\\"(.*?)\\\""; //获取img中height属性

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_font = Pattern.compile(regEx_font, Pattern.CASE_INSENSITIVE);
            m_font = p_font.matcher(htmlStr);
            htmlStr = m_font.replaceAll(""); // 过滤特殊标签

            p_width = Pattern.compile(regEx_width, Pattern.CASE_INSENSITIVE);
            m_width = p_width.matcher(htmlStr);
            htmlStr = m_width.replaceAll(""); // 过滤特殊标签

            pImg_height = Pattern.compile(regExImg_height, Pattern.CASE_INSENSITIVE);
            mImg_height = pImg_height.matcher(htmlStr);
            htmlStr = mImg_height.replaceAll(""); // 过滤特殊标签

        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlStr;// 返回文本字符串
    }
}
