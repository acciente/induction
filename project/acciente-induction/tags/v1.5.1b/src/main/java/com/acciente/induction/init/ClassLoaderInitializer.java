/*
 * Copyright 2008-2010 Acciente, LLC
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

import com.acciente.commons.loader.JavaCompiledClassDefLoader;
import com.acciente.commons.loader.ReloadingClassLoader;
import com.acciente.induction.init.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Internal.
 * This is helper class that focuses on setting up the primary classloader used
 * by the dispatcher servlet.
 *
 * @created Mar 15, 2008
 *
 * @author Adinath Raveendra Raj
 */
public class ClassLoaderInitializer
{
   public static ClassLoader getClassLoader( Config.JavaClassPath oJavaClassPathConfig, ClassLoader oParentClassLoader )
      throws ClassNotFoundException
   {
      Log oLog;

      oLog = LogFactory.getLog( ClassLoaderInitializer.class );

      if ( oJavaClassPathConfig.getDirList().size() == 0 )
      {
         return oParentClassLoader;
      }
      else
      {
         ReloadingClassLoader oClassLoader = new ReloadingClassLoader( oParentClassLoader );

         // we will ignore any dependencies for classes in any of the following packages
         oClassLoader.addIgnoredClassNamePrefix( "java." );
         oClassLoader.addIgnoredClassNamePrefix( "javax." );
         oClassLoader.addIgnoredClassNamePrefix( "com.acciente." );

         // if there is a classpath defined setup a reloading classloader to handle the specified directories
         for ( int i = 0; i < oJavaClassPathConfig.getDirList().size(); i++ )
         {
            if ( oJavaClassPathConfig.getDirList().get( i ) instanceof Config.JavaClassPath.CompiledDir )
            {
               Config.JavaClassPath.CompiledDir oCompiledDir = ( Config.JavaClassPath.CompiledDir ) oJavaClassPathConfig.getDirList().get( i );

               oLog.info( "configuring reloading classloader for compiled classes in: " + oCompiledDir.getDir() );

               // set up a compiled class definition loader
               JavaCompiledClassDefLoader oJavaCompiledClassDefLoader = new JavaCompiledClassDefLoader();
               oJavaCompiledClassDefLoader.setCompiledDirectory( oCompiledDir.getDir() );
               oJavaCompiledClassDefLoader.setPackageNamePrefix( oCompiledDir.getPackageNamePrefix() );

               // add the class def loader to the search list
               oClassLoader.addClassDefLoader( oJavaCompiledClassDefLoader );
            }
         }

         return oClassLoader;
      }
   }
}

// EOF
