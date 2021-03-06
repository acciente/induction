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
package demoapp.redirects_app;

import com.acciente.commons.htmlform.ParserException;
import com.acciente.induction.controller.Controller;
import com.acciente.induction.controller.Form;
import com.acciente.induction.controller.FormException;
import com.acciente.induction.controller.Response;
import org.apache.commons.fileupload.FileUploadException;

import java.io.IOException;

/**
 * Log
 * Jun 21, 2008 APR  -  created
 */
public class Action1Controller implements Controller
{
   public void handler( Response response ) throws IOException, InterruptedException
   {
      response.out().println( "Hello! I am method handler() in " + getClass().getName() );

      return;
   }

   public void anotherHandler( Form form, Response response ) throws IOException, InterruptedException, FormException, FileUploadException, ParserException
   {
      response.setContentType("text/html");

      response.out().println( "Hello! I am method anotherHandler() in " + getClass().getName() + "<br>" );

      if ( form.containsParam( "name" ) )
      {
         response.out().print("<br>Form: Name    : " + form.getString("name"));
      }

      if ( form.containsParam( "message" ) )
      {
         response.out().print( "<br>Form: Message : " + form.getString( "message" ) );
      }

      return;
   }
}

// EOF