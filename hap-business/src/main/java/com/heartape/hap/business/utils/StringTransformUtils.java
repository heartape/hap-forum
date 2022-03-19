package com.heartape.hap.business.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringTransformUtils {

    public String IgnoreHTML(String htmlString) {
        String regEx_html = "<[^>]+>";
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlString);
        htmlString = m_html.replaceAll(""); // 过滤html标签

        String str = htmlString.trim(); // 返回文本字符串
        Pattern filePattern = Pattern.compile("[\\\\/:*?\"<>|]");
        str = filePattern.matcher(str).replaceAll("");
        str = str.replaceAll("\\s*", "").replaceAll("", "");
        return str;
    }

    public String IgnoreBlank(String string) {
        return string.replaceAll(" ","");
    }
}
