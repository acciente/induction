/*
 * Copyright 2008-2012 Acciente, LLC
 *
 * Acciente, LLC licenses this file to you under the
 * Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.acciente.induction.dispatcher.resolver;

import com.acciente.commons.reflect.ParameterProvider;
import com.acciente.commons.reflect.ParameterProviderException;
import com.acciente.induction.controller.*;
import com.acciente.induction.dispatcher.model.ModelPool;
import com.acciente.induction.init.config.Config;
import com.acciente.induction.util.ConstructorNotFoundException;
import com.acciente.induction.util.MethodNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * Internal.
 * This class handles the resolution of parameter values used for injection into Redirect methods.
 *
 * @created Jul 05, 2010
 *
 * @author Adinath Raveendra Raj
 */
public class RedirectResolverParameterProviderFactory
{
   private ModelPool         _oModelPool;
   private Config.FileUpload _oFileUploadConfig;
   private ClassLoader       _oClassLoader;

   public RedirectResolverParameterProviderFactory( ModelPool         oModelPool,
                                                    Config.FileUpload oFileUploadConfig,
                                                    ClassLoader       oClassLoader )
   {
      _oModelPool        = oModelPool;
      _oFileUploadConfig = oFileUploadConfig;
      _oClassLoader      = oClassLoader;
   }

   public RedirectResolverParameterProvider getParameterProvider( HttpServletRequest  oRequest,
                                                                  Redirect            oRedirect  )
   {
      return new RedirectResolverParameterProvider( oRequest, oRedirect );
   }

   private class RedirectResolverParameterProvider implements ParameterProvider
   {
      private HttpServletRequest  _oRequest;
      private Redirect            _oRedirect;

      private RedirectResolverParameterProvider( HttpServletRequest  oRequest,
                                                 Redirect            oRedirect )
      {
         _oRequest  = oRequest;
         _oRedirect = oRedirect;
      }

      public Object getParameter( Class oParamClass ) throws ParameterProviderException
      {
         final String sMessagePrefix = "Redirect-resolver-parameter-provider: error resolving value for type: ";

         try
         {
            Object   oParamValue = null;

            if ( oParamClass.isAssignableFrom( Request.class ) )
            {
               oParamValue = new HttpRequest( _oRequest );
            }
            else if ( oParamClass.isAssignableFrom( Form.class ) )
            {
               oParamValue = _oModelPool.getSystemModel( oParamClass, _oRequest );
            }
            else if ( oParamClass.isAssignableFrom( HttpServletRequest.class ) )
            {
               oParamValue = _oRequest;
            }
            else if ( oParamClass.isAssignableFrom( ClassLoader.class ) )
            {
               oParamValue = _oClassLoader;
            }
            else if ( oParamClass.isAssignableFrom( Redirect.class ) )
            {
               oParamValue = _oRedirect;
            }
            else
            {
               // check to see if this is a model class
               oParamValue = _oModelPool.getModel( oParamClass, _oRequest );
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

// EOF