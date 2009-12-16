package org.jboss.jsr299.tck.tests.interceptors.definition.member;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@Target( { TYPE, METHOD })
@Retention(RUNTIME)
@Documented
@InterceptorBinding
@interface AnimalCountInterceptorBinding
{
   Operation value();
   
   enum Operation {
      INCREASE, DECREASE
   }
}
