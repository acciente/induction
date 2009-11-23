/**
 *   Copyright 2009 Acciente, LLC
 *
 *   Acciente, LLC licenses this file to you under the
 *   Apache License, Version 2.0 (the "License"); you
 *   may not use this file except in compliance with the
 *   License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in
 *   writing, software distributed under the License is
 *   distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 *   OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing
 *   permissions and limitations under the License.
 */
package com.acciente.induction.dispatcher.interceptor;

import com.acciente.commons.reflect.ParameterProvider;
import com.acciente.commons.reflect.ParameterProviderException;
import com.acciente.induction.controller.Form;
import com.acciente.induction.controller.HTMLForm;
import com.acciente.induction.controller.HttpRequest;
import com.acciente.induction.controller.HttpResponse;
import com.acciente.induction.controller.Request;
import com.acciente.induction.controller.Response;
import com.acciente.induction.dispatcher.model.ModelPool;
import com.acciente.induction.init.config.Config;
import com.acciente.induction.resolver.ControllerResolver;
import com.acciente.induction.resolver.ViewResolver;
import com.acciente.induction.template.TemplatingEngine;
import com.acciente.induction.util.ConstructorNotFoundException;
import com.acciente.induction.util.MethodNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * Internal.
 * This class handles the resolution of parameter values used for injection into request interceptor methods.
 *
 * @created Oct 20, 2009
 *
 * @author Adinath Raveendra Raj
 */
public class RequestInterceptorParameterProviderFactory
{
   private  ModelPool            _oModelPool;
   private  Config.FileUpload    _oFileUploadConfig;
   private  TemplatingEngine     _oTemplatingEngine;
   private  ClassLoader          _oClassLoader;

   public RequestInterceptorParameterProviderFactory( ModelPool         oModelPool,
                                                      Config.FileUpload oFileUploadConfig,
                                                      TemplatingEngine  oTemplatingEngine,
                                                      ClassLoader       oClassLoader )
   {
      _oModelPool          = oModelPool;
      _oFileUploadConfig   = oFileUploadConfig;
      _oTemplatingEngine   = oTemplatingEngine;
      _oClassLoader        = oClassLoader;
   }

   public RequestInterceptorParameterProvider getParameterProvider( HttpServletRequest             oRequest,
                                                                    HttpServletResponse            oResponse,
                                                                    ControllerResolver.Resolution  oControllerResolution,
                                                                    ViewResolver.Resolution        oViewResolution )
   {
      return new RequestInterceptorParameterProvider( oRequest, oResponse, oControllerResolution, oViewResolution );
   }

   private class RequestInterceptorParameterProvider implements ParameterProvider
   {
      private  HttpServletRequest               _oRequest;
      private  HttpServletResponse              _oResponse;
      private  ControllerResolver.Resolution    _oControllerResolution;
      private  ViewResolver.Resolution          _oViewResolution;

      private RequestInterceptorParameterProvider( HttpServletRequest            oRequest,
                                                   HttpServletResponse           oResponse,
                                                   ControllerResolver.Resolution oControllerResolution,
                                                   ViewResolver.Resolution       oViewResolution )
      {
         _oRequest               = oRequest;
         _oResponse              = oResponse;
         _oControllerResolution  = oControllerResolution;
         _oViewResolution        = oViewResolution;
      }

      public Object getParameter( Class oParamClass ) throws ParameterProviderException
      {
         final String sMessagePrefix = "request-interceptor-parameter-provider: error resolving value for type: ";

         try
         {
            Object   oParamValue = null;

            if ( oParamClass.isAssignableFrom( Request.class ) )
            {
               oParamValue = new HttpRequest( _oRequest );
            }
            else if ( oParamClass.isAssignableFrom( Response.class ) )
            {
               oParamValue = new HttpResponse( _oResponse );
            }
            else if ( oParamClass.isAssignableFrom( Form.class ) )
            {
               // NOTE: since the HTMLForm is per-request no caching is needed, since parameters
               // are resolved before controller invocation, and become local variables in the
               // controller for the duration of the request
               oParamValue = new HTMLForm( _oRequest, _oFileUploadConfig );
            }
            else if ( oParamClass.isAssignableFrom( HttpServletRequest.class ) )
            {
               oParamValue = _oRequest;
            }
            else if ( oParamClass.isAssignableFrom( HttpServletResponse.class ) )
            {
               oParamValue = _oResponse;
            }
            else if ( oParamClass.isAssignableFrom( ControllerResolver.Resolution.class ) )
            {
               oParamValue = _oControllerResolution;
            }
            else if ( oParamClass.isAssignableFrom( ViewResolver.Resolution.class ) )
            {
               oParamValue = _oViewResolution;
            }
            else if ( oParamClass.isAssignableFrom( TemplatingEngine.class ) )
            {
               oParamValue = _oTemplatingEngine;
            }
            else if ( oParamClass.isAssignableFrom( ClassLoader.class ) )
            {
               oParamValue = _oClassLoader;
            }
            else
            {
               // check to see if this is a model class
               oParamValue = _oModelPool.getModel( oParamClass.getName(), _oRequest );
            }

            if ( oParamValue == null )
            {
               throw ( new ParameterProviderException( sMessagePrefix + oParamClass ) );
            }

            return oParamValue;
         }
         catch ( MethodNotFoundException e )
         {  throw new ParameterProviderException( sMessagePrefix + oParamClass, e );     }
         catch ( InvocationTargetException e )
         {  throw new ParameterProviderException( sMessagePrefix + oParamClass, e );     }
         catch ( ClassNotFoundException e )
         {  throw new ParameterProviderException( sMessagePrefix + oParamClass, e );     }
         catch ( ConstructorNotFoundException e )
         {  throw new ParameterProviderException( sMessagePrefix + oParamClass, e );     }
         catch ( IllegalAccessException e )
         {  throw new ParameterProviderException( sMessagePrefix + oParamClass, e );     }
         catch ( InstantiationException e )
         {  throw new ParameterProviderException( sMessagePrefix + oParamClass, e );     }
      }
   }
}