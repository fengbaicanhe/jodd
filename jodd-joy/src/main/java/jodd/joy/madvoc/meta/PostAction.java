// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.joy.madvoc.meta;

import jodd.madvoc.meta.Action;
import jodd.madvoc.path.ActionNamingStrategy;
import jodd.madvoc.result.ActionResult;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Post action annotation. Extension is set to '<b>NONE</b>' and method
 * is set to '<b>POST</b>'.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Action(method = "POST")
public @interface PostAction {

	String value() default "";

	String extension() default Action.NONE;

	String alias() default "";

	boolean async() default false;

	Class<? extends ActionResult> result() default ActionResult.class;

	Class<? extends ActionNamingStrategy> path() default ActionNamingStrategy.class;

}