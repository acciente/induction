package com.acciente.dragonfly.controller;

/**
 * This interface is used to mark a specific class as a controller. This interface exists
 * primarily to enhance security by explicitly marking a class that may be invoked as a
 * controller by the framework.
 *
 * A class implementing this interface is expected to have a single public contructor
 * adhering to the following convention:
 *   - the single constructor should accepts no arguments or
 *   - the single constructor should declare formal parameters using only the
 *     following types,
 *     - javax.servlet.ServletContext
 *     - javax.servlet.ServletConfig
 *
 * A class implementing this interface may optionally define a single public method
 * named destroy(). If defined this method will be called when the controller instance is
 * taken out of service. If there is more than one destroy method or if it the single
 * destroy method requires arguments then the method will be ignored.
 *
 * Log
 * Mar 14, 2008 APR  -  created
 */
public interface Controller
{
   public static final String DESTRUCTOR_METHOD_NAME = "destroy";
}

// EOF