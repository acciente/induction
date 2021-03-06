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
package demoapp.models_app;

import com.acciente.induction.controller.Controller;
import com.acciente.induction.controller.Response;

import java.io.IOException;

/**
 * A simple controller
 *
 * Log
 * Jun 20, 2008 APR  -  created
 */
public class FooController implements Controller
{
   public void handler( Response response, FooModel fooModel ) throws IOException
   {
      response.setContentType( "text/plain" );

      response.out().println( "FooModel               : " + fooModel );
      response.out().println( "FooModel.getBarModel() : " + fooModel.getBarModel());
   }
}

// EOF