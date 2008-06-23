/**
 *   Copyright 2008 Acciente, LLC
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
package com.acciente.induction.dispatcher.controller;

/**
 * This class ...
 *
 * Log
 * Apr 17, 2008 APR  -  created
 */
public class ControllerExecutorException extends Exception
{
   public ControllerExecutorException( String sMessage )
   {
      super( sMessage );
   }

   public ControllerExecutorException( String sMessage, Throwable oCause )
   {
      super( sMessage, oCause );
   }
}

// EOF