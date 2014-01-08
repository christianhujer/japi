package de.riedquat.http.rest;

import de.riedquat.http.Http11Method;
import de.riedquat.http.HttpOutputStream;

import java.lang.annotation.*;

/**
 * Marks a method as an HTTP method for a REST interface.
 *
 * A method is a RestMethod if it fulfills all of the following conditions:
 * <ul>
 *  <li>It is annotated with this interface {@link RestMethod}.</li>
 *  <li>It is public</li>
 *  <li>It is not static</li>
 *  <li>It takes two parameters, the first one of type {@link RestInputStream}, the second one of type {@link RestOutputStream}.</li>
 * </ul>
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
// TODO:2009-10-08:christianhujer:Consider and discuss, how the concept of a class containing RestMethods and thus being mapped to multiple URIs relates to WebDAV.
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RestMethod {

    /**
     * The URI.
     *
     * @return The URI.
     */
    String value();

    /** The supported methods.
     * Legal values are:
     * <ul>
     *  <li>{@link Http11Method#GET}</li>
     *  <li>{@link Http11Method#POST}</li>
     *  <li>{@link Http11Method#PUT}</li>
     *  <li>{@link Http11Method#DELETE}</li>
     * </ul>
     * @note It is unnecessary and illegal to specify {@link Http11Method#HEAD} here.
     *       HEAD is automatically handled by {@link HttpOutputStream}.
     *       Just implement GET as usual, and HEAD will automatically work.
     * @return The supported methods.
     */
    String[] methods() default { Http11Method.GET };

    /** Whether or not a RestProcessor shall apply this RestMethod to child paths of {@link #value() the uri} as well.
     * @return <code>true</code> if a RestProcessor shall scan child paths, otherwise <code>false</code> (default).
     */
    // TODO:2009-10-08:christianhujer:This should be replaced by a more flexible variant.
    //         There should be 4 options available:
    //         - Exact match
    //         - Shell Glob (bash 4 / Ant style)
    //         - Regular Expression
    //         - Leading
    boolean scanChildren() default false;
}
