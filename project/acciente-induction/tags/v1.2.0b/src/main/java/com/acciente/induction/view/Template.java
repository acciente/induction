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
package com.acciente.induction.view;

/**
 * This view interface should be implemented to render a text template to the browser.
 *
 * @created Mar 9, 2008
 *
 * @author Adinath Raveendra Raj
 */
public interface Template
{
   /**
    * Returns the name of the template that dragon will use to render the
    * contents an instance of this class.
    *
    * @return a string representing a template name
    */
   String getTemplateName();

   /**
    * This method should return the mime type of the text content
    *
    * @return a string representation of a mime type, e.g. text/html
    */
   String getMimeType();
}

// EOF