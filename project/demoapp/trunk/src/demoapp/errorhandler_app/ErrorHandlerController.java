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
package demoapp.errorhandler_app;

import com.acciente.induction.controller.Controller;
import com.acciente.induction.controller.Response;

import java.io.IOException;

/**
 * ErrorHandlerController
 *
 * @author Adinath Raveendra Raj
 * @created Nov 30, 2009
 */
public class ErrorHandlerController implements Controller
{
   public Class handler( Response oResponse, Throwable oError ) throws IOException
   {
      oResponse.out().println( "Oops..you encountered an error in the demoapp, this is the error handler controller @ " + getClass().getName() );

      if ( oError != null )
      {
         oResponse.out().println();
         oResponse.out().println( "----------------------------------------" );
         oResponse.out().println( "Error: " + oError );

         for ( oError = oError.getCause(); oError != null; oError = oError.getCause() )
         {
            oResponse.out().println( "Cause: " + oError );
         }
      }

      return ErrorView.class; 
   }
}
