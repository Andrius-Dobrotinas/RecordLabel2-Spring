package com.andrewd.recordlabel.web.components.cookies;

import javax.servlet.http.HttpServletResponse;

public interface CookieGenerator {
    String JSESSIONID = "JSESSIONID";

    void setCookieName(String cookieName);
    void removeCookie(HttpServletResponse response);
}