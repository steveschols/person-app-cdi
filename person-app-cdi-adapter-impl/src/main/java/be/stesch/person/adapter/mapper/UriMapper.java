package be.stesch.person.adapter.mapper;

import org.mapstruct.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by u420643 on 2/9/2017.
 */
@Qualifier
@Target(METHOD)
@Retention(SOURCE)
public @interface UriMapper {
}
