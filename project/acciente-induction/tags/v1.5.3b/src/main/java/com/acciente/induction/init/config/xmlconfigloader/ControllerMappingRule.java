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
package com.acciente.induction.init.config.xmlconfigloader;

import com.acciente.commons.lang.Strings;
import com.acciente.induction.init.config.Config;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Internal.
 * ControllerMappingRule
 *
 * NOTE: we do not extend Rule in this class, since this class while a rules "container",
 * but is not itself a rule
 *
 * @created Mar 29, 2009
 *
 * @author Adinath Raveendra Raj
 */
public class ControllerMappingRule extends Rule
{
   private  Config.ControllerMapping   _oControllerMapping;

   private  String                     _sDefaultHandlerMethodName;
   private  Boolean                    _oIgnoreMethodNameCase;

   public ControllerMappingRule( Config.ControllerMapping oControllerMapping )
   {
      _oControllerMapping = oControllerMapping;
   }

   public SetDefaultHandlerMethodRule createSetDefaultHandlerMethodRule()
   {
      return new SetDefaultHandlerMethodRule();
   }

   public SetIgnoreHandlerMethodCaseRuleRule createSetIgnoreHandlerMethodCaseRule()
   {
      return new SetIgnoreHandlerMethodCaseRuleRule();
   }

   public AddURLToClassMapRule createAddURLToClassMapRule()
   {
      return new AddURLToClassMapRule();
   }

   public AddErrorToClassMapRule createAddErrorToClassMapRule()
   {
      return new AddErrorToClassMapRule();
   }

   public void begin( String sNamespace, String sName, Attributes oAttributes )
   {
      // reset data stored in rule
      _sDefaultHandlerMethodName = null;
      _oIgnoreMethodNameCase     = null;
   }

   public void end( String sNamespace, String sName ) throws XMLConfigLoaderException
   {
      if ( _oIgnoreMethodNameCase != null )
      {
         _oControllerMapping.setIgnoreMethodNameCase( _oIgnoreMethodNameCase.booleanValue() );
      }

      if ( ! Strings.isEmpty( _sDefaultHandlerMethodName ) )
      {
         _oControllerMapping.setDefaultHandlerMethodName( _sDefaultHandlerMethodName );
      }
   }

   private class SetDefaultHandlerMethodRule extends Rule
   {
      public void body( String sNamespace, String sName, String sText ) throws XMLConfigLoaderException
      {
         if ( Strings.isEmpty( sText ) )
         {
            throw new XMLConfigLoaderException( "config > controller-mapping > default-handler-method: must specify a valid method name" );
         }
         _sDefaultHandlerMethodName = sText;
      }
   }

   private class SetIgnoreHandlerMethodCaseRuleRule extends Rule
   {
      public void body( String sNamespace, String sName, String sText ) throws XMLConfigLoaderException
      {
         if ( Strings.isEmpty( sText ) )
         {
            throw new XMLConfigLoaderException( "config > controller-mapping > ignore-handler-method-case: must specify a boolean value, specify true or false" );
         }
         _oIgnoreMethodNameCase = Boolean.valueOf( sText );
      }
   }

   public class AddURLToClassMapRule extends Rule
   {
      private  Pattern  _oURLPattern;
      private  String[] _oClassPackages;
      private  Pattern  _oClassPattern;
      private  List     _oClassFindReplaceDirectives;

      public void begin( String sNamespace, String sName, Attributes oAttributes )
      {
         // reset data stored in rule
         _oURLPattern                  = null;
         _oClassPackages               = null;
         _oClassPattern                = null;
         _oClassFindReplaceDirectives  = new ArrayList();
      }

      public void end( String sNamespace, String sName ) throws XMLConfigLoaderException
      {
         if ( _oURLPattern == null )
         {
            throw new XMLConfigLoaderException( "config > controller-mapping > url-to-class-map > URL pattern is a required attribute" );
         }

         if ( _oClassPackages == null )
         {
            throw new XMLConfigLoaderException( "config > controller-mapping > url-to-class-map > class packages is a required attribute" );
         }

         if ( _oClassPattern == null )
         {
            throw new XMLConfigLoaderException( "config > controller-mapping > url-to-class-map > class pattern is a required attribute" );
         }

         Config.ControllerMapping.URLToClassMap oURLToClassMap
            = _oControllerMapping.addURLToClassMap( _oURLPattern, _oClassPackages, _oClassPattern );

         for ( int i = 0; i < _oClassFindReplaceDirectives.size(); i++)
         {
            String[] asFindReplace = ( String[] ) _oClassFindReplaceDirectives.get( i );

            oURLToClassMap.addClassFindReplaceDirective( asFindReplace[ 0 ], asFindReplace[ 1 ] );
         }
      }

      public ParamURLPatternRule createParamURLPatternRule()
      {
         return new ParamURLPatternRule();
      }

      public ParamClassPackagesRule createParamClassPackagesRule()
      {
         return new ParamClassPackagesRule();
      }

      public ParamClassPatternRule createParamClassPatternRule()
      {
         return new ParamClassPatternRule();
      }

      public AddClassFindReplaceDirectiveRule createAddClassFindReplaceDirectiveRule()
      {
         return new AddClassFindReplaceDirectiveRule();
      }

      private class ParamURLPatternRule extends Rule
      {
         public void body( String sNamespace, String sName, String sText )
         {
            _oURLPattern = Pattern.compile( sText, Pattern.CASE_INSENSITIVE );
         }
      }

      private class ParamClassPackagesRule extends Rule
      {
         public void body( String sNamespace, String sName, String sText )
         {
            _oClassPackages = sText.split( ";|," );
         }
      }

      private class ParamClassPatternRule extends Rule
      {
         public void body( String sNamespace, String sName, String sText )
         {
            _oClassPattern = Pattern.compile( sText );
         }
      }

      public class AddClassFindReplaceDirectiveRule extends Rule
      {
         private  String   _sFindStr;
         private  String   _sReplaceStr;

         public void begin( String sNamespace, String sName, Attributes oAttributes )
         {
            // reset data stored in rule
            _sFindStr      = null;
            _sReplaceStr   = null;
         }

         public void end( String sNamespace, String sName ) throws XMLConfigLoaderException
         {
            if ( _sFindStr == null )
            {
               throw new XMLConfigLoaderException( "config > controller-mapping > url-to-class-map > class-replace > find string is a required attribute" );
            }

            if ( _sReplaceStr == null )
            {
               throw new XMLConfigLoaderException( "config > controller-mapping > url-to-class-map > class-replace > replace string is a required attribute" );
            }

            _oClassFindReplaceDirectives.add( new String[]{ _sFindStr, _sReplaceStr } );
         }

         public ParamFindRule createParamFindRule()
         {
            return new ParamFindRule();
         }

         public ParamReplaceRule createParamReplaceRule()
         {
            return new ParamReplaceRule();
         }

         private class ParamFindRule extends Rule
         {
            public void body( String sNamespace, String sName, String sText )
            {
               _sFindStr = sText;
            }
         }

         private class ParamReplaceRule extends Rule
         {
            public void body( String sNamespace, String sName, String sText )
            {
               _sReplaceStr = sText;
            }
         }
      }
   }

   public class AddErrorToClassMapRule extends Rule
   {
      private String    _sExceptionPattern_ClassName;
      private Boolean   _bExceptionPattern_IncludeDerived;
      private String    _sClassName;
      private String    _sClassMethodName;

      private boolean   _bExceptionPatternProvided;

      public void begin( String sNamespace, String sName, Attributes oAttributes )
      {
         // reset data stored in rule
         _bExceptionPatternProvided          = false;
         _sExceptionPattern_ClassName        = null;
         _bExceptionPattern_IncludeDerived   = null;
         _sClassName                         = null;
         _sClassMethodName                   = null;
      }

      public void end( String sNamespace, String sName ) throws XMLConfigLoaderException
      {
         if ( _sClassName == null )
         {
            throw new XMLConfigLoaderException( "config > controller-mapping > error-to-class-map > class name is a required attribute" );
         }

         Config.ControllerMapping.ErrorToClassMap oErrorToClassMap
            = _oControllerMapping.addErrorToClassMap( _sClassName, _sClassMethodName );

         if ( _bExceptionPatternProvided )
         {
            if ( _sExceptionPattern_ClassName == null  )
            {
               throw new XMLConfigLoaderException( "config > controller-mapping > error-to-class-map > exception-pattern > class name is a required attribute" );
            }
            // optional IncludeDerived param gets default value true if ommitted
            oErrorToClassMap.setExceptionPattern( _sExceptionPattern_ClassName,
                                                  _bExceptionPattern_IncludeDerived != null
                                                   ? _bExceptionPattern_IncludeDerived.booleanValue()
                                                   : true );
         }
      }

      public ExceptionPatternRule createExceptionPatternRule()
      {
         return new ExceptionPatternRule();
      }

      public ParamClassNameRule createParamClassNameRule()
      {
         return new ParamClassNameRule();
      }

      public ParamClassMethodNameRule createParamClassMethodNameRule()
      {
         return new ParamClassMethodNameRule();
      }

      public class ExceptionPatternRule extends Rule
      {
         public void begin( String sNamespace, String sName, Attributes oAttributes )
         {
            // reset data stored in rule
            _sExceptionPattern_ClassName        = null;
            _bExceptionPattern_IncludeDerived   = null;
            _bExceptionPatternProvided          = true;
         }

         public ParamClassNameRule createParamClassNameRule()
         {
            return new ParamClassNameRule();
         }

         public ParamIncludeDerivedRule createParamIncludeDerivedRule()
         {
            return new ParamIncludeDerivedRule();
         }

         private class ParamClassNameRule extends Rule
         {
            public void body( String sNamespace, String sName, String sText )
            {
               _sExceptionPattern_ClassName = sText;
            }
         }

         private class ParamIncludeDerivedRule extends Rule
         {
            public void body( String sNamespace, String sName, String sText )
            {
               _bExceptionPattern_IncludeDerived = Boolean.valueOf( sText );
            }
         }
      }

      private class ParamClassNameRule extends Rule
      {
         public void body( String sNamespace, String sName, String sText )
         {
            _sClassName = sText;
         }
      }

      private class ParamClassMethodNameRule extends Rule
      {
         public void body( String sNamespace, String sName, String sText )
         {
            _sClassMethodName = sText;
         }
      }
   }
}