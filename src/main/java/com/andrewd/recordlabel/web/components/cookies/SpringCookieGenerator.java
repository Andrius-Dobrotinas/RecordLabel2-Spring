package com.andrewd.recordlabel.web.components.cookies;

import org.springframework.stereotype.Component;

/**
 * A pseudo-wrapper for Spring CookieGenerator that implements an interface and therefore
 * may be injectable and used with unit tested classes
 */
@Component
public class SpringCookieGenerator extends org.springframework.web.util.CookieGenerator
implements CookieGenerator{

}
