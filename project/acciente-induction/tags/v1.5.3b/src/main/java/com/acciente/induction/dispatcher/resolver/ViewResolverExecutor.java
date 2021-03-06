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

import com.acciente.commons.reflect.Invoker;
import com.acciente.commons.reflect.ParameterProviderException;
import com.acciente.induction.resolver.ViewResolver;
import com.acciente.induction.util.MethodNotFoundException;
import com.acciente.induction.util.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Induction Internal class.
 *
 * @author Adinath Raveendra Raj
 * @created Jul 5, 2010
 */
public class ViewResolverExecutor
{
   private ViewResolver                         _oViewResolver;
   private ViewResolverParameterProviderFactory _oViewResolverParameterProviderFactory;

   private Method                               _oMethod_resolveRequest;

   public ViewResolverExecutor( ViewResolver                         oViewResolver,
                                ViewResolverParameterProviderFactory oViewResolverParameterProviderFactory )
      throws MethodNotFoundException
   {
      _oViewResolver                         = oViewResolver;
      _oViewResolverParameterProviderFactory = oViewResolverParameterProviderFactory;

      _oMethod_resolveRequest                = ReflectUtils.getSingletonMethod ( _oViewResolver.getClass ( ), "resolveRequest" );
   }

   public ViewResolver.Resolution resolveRequest( HttpServletRequest oRequest )
   {
      Object oReturnValue;

      try
      {
         oReturnValue = Invoker.invoke( _oMethod_resolveRequest,
                                        _oViewResolver,
                                        null,
                                        _oViewResolverParameterProviderFactory.getParameterProvider( oRequest ) );
      }
      catch ( InvocationTargetException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IllegalAccessException e )
      {
         throw new RuntimeException( e );
      }
      catch ( ParameterProviderException e )
      {
         throw new RuntimeException( e );
      }

      if ( oReturnValue != null && ! ( oReturnValue instanceof ViewResolver.Resolution ) )
      {
         throw new IllegalArgumentException( "view resolver: resolveRequest(...) did not return a value of type ViewResolver.Resolution, returned type was: "
                                             + oReturnValue.getClass() );
      }

      return ( ViewResolver.Resolution ) oReturnValue;
   }
}
