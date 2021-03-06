/*
 * Copyright 2008-2013 Acciente, LLC
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
package com.acciente.induction.init;

import com.acciente.commons.lang.Strings;
import com.acciente.commons.reflect.ParameterProviderException;
import com.acciente.induction.dispatcher.model.ModelPool;
import com.acciente.induction.init.config.Config;
import com.acciente.induction.resolver.RedirectResolver;
import com.acciente.induction.resolver.ShortURLRedirectResolver;
import com.acciente.induction.util.ConstructorNotFoundException;
import com.acciente.induction.util.ObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Internal.
 *
 * @created Jun 21, 2008 *
 * @author Adinath Raveendra Raj
 */
public class RedirectResolverInitializer
{
   public static RedirectResolver getRedirectResolver( Config.RedirectResolver   oRedirectResolverConfig,
                                                       Config.RedirectMapping    oRedirectMappingConfig,
                                                       ModelPool                 oModelPool,
                                                       ClassLoader               oClassLoader,
                                                       ServletConfig             oServletConfig )
      throws ClassNotFoundException, InvocationTargetException, ConstructorNotFoundException, ParameterProviderException, IllegalAccessException, InstantiationException, IOException
   {
      RedirectResolver     oRedirectResolver;
      String               sRedirectResolverClassName;
      Log                  oLog;

      oLog = LogFactory.getLog( RedirectResolverInitializer.class );

      sRedirectResolverClassName = oRedirectResolverConfig.getClassName();

      if ( Strings.isEmpty( sRedirectResolverClassName ) )
      {
         oRedirectResolver = new ShortURLRedirectResolver( oRedirectMappingConfig, oClassLoader );
      }
      else
      {
         oLog.info( "loading user-defined redirect resolver: " + sRedirectResolverClassName );

         Class    oRedirectResolverClass  = oClassLoader.loadClass( sRedirectResolverClassName );

         // attempt to find and call the single public constructor
         oRedirectResolver
            =  ( RedirectResolver )
               ObjectFactory.createObject( oRedirectResolverClass,
                                           new Object[]{ oServletConfig,
                                                         oRedirectResolverConfig,
                                                         oRedirectMappingConfig,
                                                         oClassLoader },
                                           new InitializerParameterProvider( oModelPool, "redirect-resolver-init" )
                                         );
      }

      return oRedirectResolver;
   }
}