/*
 * Copyright 2008-2011 Acciente, LLC
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
package com.acciente.induction.dispatcher.controller;

/**
 * Internal.
 *
 * @created Apr 17, 2008
 *
 * @author Adinath Raveendra Raj
 */
public class ControllerExecutorException extends Exception
{
   private  String   _sControllerClassName;
   private  String   _sControllerMethodName;

   public ControllerExecutorException( String sControllerClassName, String sControllerMethodName, String sMessage )
   {
      super( sMessage );

      _sControllerClassName   = sControllerClassName;
      _sControllerMethodName  = sControllerMethodName;
   }

   public ControllerExecutorException( String sControllerClassName, String sControllerMethodName, String sMessage, Throwable oCause )
   {
      super( sMessage, oCause );

      _sControllerClassName   = sControllerClassName;
      _sControllerMethodName  = sControllerMethodName;
   }

   public String getControllerClassName()
   {
      return _sControllerClassName;
   }

   public String getControllerMethodName()
   {
      return _sControllerMethodName;
   }

   public String getMessage()
   {
      return "Controller: " + _sControllerClassName + ", " + super.getMessage();
   }
}

// EOF