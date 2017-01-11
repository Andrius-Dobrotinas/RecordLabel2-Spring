package com.andrewd.recordlabel.web.cookie;

import org.springframework.stereotype.Component;

/**
 * A pseudo-wrapper for Spring CookieGenerator that implements an interface and therefore
 * may be used in classes that are unit tested
 */
@Component
public class SpringCookieGenerator extends org.springframework.web.util.CookieGenerator
implements CookieGenerator{

}
