package com.teamacronymcoders.advisor.json.constructor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonProperty {
    String value();

    boolean required() default false;
}
