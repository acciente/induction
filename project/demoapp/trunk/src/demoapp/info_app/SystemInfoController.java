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
package demoapp.info_app;

import com.acciente.induction.controller.Controller;
import com.acciente.induction.controller.Response;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * SystemInfoController
 *
 * @author Adinath Raveendra Raj
 * @created Apr 2, 2009
 */
public class SystemInfoController implements Controller
{
   public void handler( Response response, ClassLoader classLoader )
      throws IOException
   {
      ClassLoader oPreviousClassLoader = null;

      int iCount = 0;

      while ( classLoader != null && classLoader != oPreviousClassLoader )
      {
         response.out().println( "\nclassLoader-" + iCount + " > " + classLoader );

         if ( classLoader instanceof URLClassLoader )
         {
            response.out().println( "classLoader-" + iCount + " > is a URL class loader" );

            URL[] aoURL = ( ( URLClassLoader ) classLoader ).getURLs();

            for ( int i = 0; i < aoURL.length; i++ )
            {
               response.out().println( "\t\tclassLoader-" + iCount + " > URL: " + aoURL[ i ] );
            }
         }

         oPreviousClassLoader = classLoader;
         classLoader          = classLoader.getParent();

         iCount++;
      }
   }   
}
