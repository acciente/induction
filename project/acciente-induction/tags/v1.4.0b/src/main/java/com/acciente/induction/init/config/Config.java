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
package com.acciente.induction.init.config;

import com.acciente.commons.lang.Strings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class is the container for the configuration information used by Induction.
 * It uses inner classes to represent each type of category of configuration information,
 * sometimes there may be two identical looking inner classes but this is done since, though
 * identical, the two classes represent different concepts and as such may diverge in the future.
 *
 * @created Feb 29, 2008
 *
 * @author Adinath Raveendra Raj
 */
public class Config
{
   private JavaClassPath         _oJavaClassPath         = new JavaClassPath();
   private ModelDefs             _oModelDefs             = new ModelDefs();
   private Templating            _oTemplating            = new Templating();
   private ControllerMapping     _oControllerMapping     = new ControllerMapping();
   private ViewMapping           _oViewMapping           = new ViewMapping();
   private RedirectMapping       _oRedirectMapping       = new RedirectMapping();
   private RequestInterceptors   _oRequestInterceptors   = new RequestInterceptors();
   private ControllerResolver    _oControllerResolver    = new ControllerResolver();
   private ViewResolver          _oViewResolver          = new ViewResolver();
   private RedirectResolver      _oRedirectResolver      = new RedirectResolver();
   private FileUpload            _oFileUpload            = new FileUpload();

   /**
    * Defined the classpath to be used for loading java class files. The classpath is
    * a list of directories, each of which may contain java class sources or compiled
    * class files. Whatever classpath effective with the default classloader prevails,
    * otherwise the paths defined by this configuration are searched after the default
    * system defined classpath is searched.
    *
    * @return a java classpath definition
    */
   public JavaClassPath getJavaClassPath()
   {
      return _oJavaClassPath;
   }

   /**
    * This method provides access to the configuration of the template engine.
    *
    * @return a configuration object
    */
   public Templating getTemplating()
   {
      return _oTemplating;
   }

   /**
    * This method is used to access the model definitions, in particular to add to, and read from
    * the model definitions stored in this configuration.
    *
    * @return an object reference that keeps the model definition information
    */
   public ModelDefs getModelDefs()
   {
      return _oModelDefs;
   }

   /**
    * This method is used to access the settings used to configure the controller mapping.
    *
    * @return an object reference that keeps the controller mapping config settings
    */
   public ControllerMapping getControllerMapping()
   {
      return _oControllerMapping;
   }

   /**
    * This method is used to access the settings used to configure the view mapping.
    *
    * @return an object reference that keeps the view mapping config settings
    */
   public ViewMapping getViewMapping()
   {
      return _oViewMapping;
   }

   /**
    * This method is used to access the settings used to configure the redirect mapping.
    *
    * @return an object reference that keeps the redirect mapping config settings
    */
   public RedirectMapping getRedirectMapping()
   {
      return _oRedirectMapping;
   }

   /**
    * This method is used to access the configured request interceptor list.
    *
    * @return an object reference that keeps the request interceptor list config settings
    */
   public RequestInterceptors getRequestInterceptors()
   {
      return _oRequestInterceptors;
   }

   /**
    * This method is used to access the settings used to configure the controller resolver.
    *
    * @return an object reference that keeps the controller resolver config settings
    */
   public ControllerResolver getControllerResolver()
   {
      return _oControllerResolver;
   }

   /**
    * This method is used to access the settings used to configure the view resolver.
    *
    * @return an object reference that keeps the view resolver config settings
    */
   public ViewResolver getViewResolver()
   {
      return _oViewResolver;
   }

   /**
    * This method is used to access the settings used to configure the redirect resolver.
    *
    * @return an object reference that keeps the redirect resolver config settings
    */
   public RedirectResolver getRedirectResolver()
   {
      return _oRedirectResolver;
   }

   /**
    * This method is used to access config parameters that control file uploads
    *
    * @return an object reference that keeps the file upload settings
    */
   public FileUpload getFileUpload()
   {
      return _oFileUpload;
   }

   public String toString()
   {
      return toXML();
   }

   public String toXML()
   {
      StringBuffer   oBuffer = new StringBuffer();

      oBuffer.append( XML.Config.OPEN_IND );

      oBuffer.append( _oJavaClassPath.toXML() );
      oBuffer.append( _oModelDefs.toXML() );
      oBuffer.append( _oTemplating.toXML() );

      oBuffer.append( _oControllerMapping.toXML() );
      oBuffer.append( _oViewMapping.toXML() );
      oBuffer.append( _oRedirectMapping.toXML() );

      oBuffer.append( _oRequestInterceptors.toXML() );

      oBuffer.append( _oControllerResolver.toXML() );
      oBuffer.append( _oViewResolver.toXML() );
      oBuffer.append( _oRedirectResolver.toXML() );

      oBuffer.append( _oFileUpload.toXML() );
      oBuffer.append( "\n" );
      oBuffer.append( XML.Config.CLOSE_IND );

      return oBuffer.toString();
   }

   /**
    * Modular configuration container
    */
   public static class JavaClassPath
   {
      private  List           _oDirList      = new ArrayList();

      public void addCompiledDir( File oDir, String sPackagePrefixName )
      {
         _oDirList.add( new CompiledDir( oDir, sPackagePrefixName ) );
      }

      public List getDirList()
      {
         return _oDirList;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         if ( _oDirList.size() == 0 )
         {
            return "";
         }
         else
         {
            StringBuffer   oBuffer = new StringBuffer();

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_JavaClassPath.OPEN_IND );

            for ( Iterator oIter = _oDirList.iterator(); oIter.hasNext(); )
            {
               Object oPathItem = oIter.next();

               if ( oPathItem instanceof CompiledDir )
               {
                  oBuffer.append ( ( ( CompiledDir ) oPathItem ).toXML() );
               }
               else
               {
                  throw new IllegalArgumentException( "config-error: internal error: unknown java class path item : " + oPathItem + ", of type: " + oPathItem.getClass() );
               }
            }

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_JavaClassPath.CLOSE_IND );

            return oBuffer.toString();
         }
      }

      /**
       * Modular configuration container
       */
      public static class CompiledDir
      {
         private  File     _oDir;
         private  String   _sPackageNamePrefix;

         private CompiledDir( File oDir, String sPackagePrefixName )
         {
            _oDir                =  oDir;
            _sPackageNamePrefix  =  sPackagePrefixName;
         }

         public File getDir()
         {
            return _oDir;
         }

         public String getPackageNamePrefix()
         {
            return _sPackageNamePrefix;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            return
               XML.Config_JavaClassPath_CompiledDirectory
                  .toXML( XML.Config_JavaClassPath_CompiledDirectory_Directory.toXML( _oDir )
                          + XML.Config_JavaClassPath_CompiledDirectory_PackagePrefix.toXML( _sPackageNamePrefix )
                        );
         }
      }
   }

   /**
    * Modular configuration container
    */
   public static class ModelDefs
   {
      private Map _oModelDefMap = new HashMap();

      /**
       * Adds a new model definition
       *
       * @param sModelClassName string representing a fully qualified model classname
       * @param sModelFactoryClassName string representing a fully qualified model factory classname
       * @param bIsApplicationScope true if a single model object should be created per application
       * @param bIsSessionScope true if a single model object should be created per session
       * @param bIsRequestScope true if a single model object should be created per request
       * @param bIsInitOnStartUp true if this model should be created on system startup, otherwise all models are
       * created on demand. This attribute is only valid for application scope models
       */
      public void addModelDef( String     sModelClassName,
                               String     sModelFactoryClassName,
                               boolean    bIsApplicationScope,
                               boolean    bIsSessionScope,
                               boolean    bIsRequestScope,
                               boolean    bIsInitOnStartUp )
      {
         sModelClassName = sModelClassName.trim();

         if ( _oModelDefMap.containsKey( sModelClassName ) )
         {
            throw new IllegalArgumentException( "config-error: model classname: " + sModelClassName + " already defined" );
         }

         if ( bIsInitOnStartUp && ! bIsApplicationScope )
         {
            throw new IllegalArgumentException( "config-error: model classname: "
                                                + sModelClassName
                                                + " init on startup may only be turned on application scope models" );
         }

         _oModelDefMap.put( sModelClassName, new ModelDef( sModelClassName, sModelFactoryClassName, bIsApplicationScope, bIsSessionScope, bIsRequestScope, bIsInitOnStartUp ) );
      }

      public ModelDef getModelDef( String sModelClassName )
      {
         return ( ModelDef ) _oModelDefMap.get( sModelClassName );
      }

      public Collection getModelDefList()
      {
         return _oModelDefMap.values();
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         if ( _oModelDefMap.size() == 0 )
         {
            return "";
         }
         else
         {
            StringBuffer   oBuffer = new StringBuffer();

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_ModelDefs.OPEN_IND );

            for ( Iterator oIter = _oModelDefMap.values().iterator(); oIter.hasNext(); )
            {
               oBuffer.append( ( ( ModelDef ) oIter.next() ).toXML() );
            }

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_ModelDefs.CLOSE_IND );

            return oBuffer.toString();
         }
      }

      /**
       * Modular configuration container
       */
      public static class ModelDef
      {
         private String    _sModelClassName;
         private String    _sModelFactoryClassName;
         private boolean   _bIsApplicationScope;
         private boolean   _bIsSessionScope;
         private boolean   _bIsRequestScope;
         private boolean   _bIsInitOnStartUp;

         private  ModelDef( String     sModelClassName,
                            String     sModelFactoryClassName,
                            boolean    bIsApplicationScope,
                            boolean    bIsSessionScope,
                            boolean    bIsRequestScope,
                            boolean    bIsInitOnStartUp )
         {
            if ( sModelClassName == null )
            {
               throw new IllegalArgumentException( "config-error: model classname not defined" );
            }

            sModelClassName = sModelClassName.trim();

            if ( sModelClassName.length() == 0 )
            {
               throw new IllegalArgumentException( "config-error: model classname empty" );
            }

            if ( ! ( bIsApplicationScope || bIsSessionScope || bIsRequestScope ) )
            {
               throw new IllegalArgumentException( "config-error: model scope not defined" );
            }

            // check that only one of the booleans has a true value
            if ( bIsApplicationScope )
            {
               if ( bIsSessionScope || bIsRequestScope )
               {
                  throw new IllegalArgumentException( "config-error: model scope defined ambiguously" );
               }
            }
            else if ( bIsSessionScope )
            {
               if ( bIsRequestScope )
               {
                  throw new IllegalArgumentException( "config-error: model scope defined ambiguously" );
               }
            }

            _sModelClassName        = sModelClassName;
            _sModelFactoryClassName = sModelFactoryClassName;
            _bIsApplicationScope    = bIsApplicationScope;
            _bIsSessionScope        = bIsSessionScope;
            _bIsRequestScope        = bIsRequestScope;
            _bIsInitOnStartUp       = bIsInitOnStartUp;
         }

         public String getModelClassName()
         {
            return _sModelClassName;
         }

         public String getModelFactoryClassName()
         {
            return _sModelFactoryClassName;
         }

         public boolean hasModelFactoryClassName()
         {
            return _sModelFactoryClassName != null && _sModelFactoryClassName.trim().length() != 0;
         }

         public boolean isApplicationScope()
         {
            return _bIsApplicationScope;
         }

         public boolean isSessionScope()
         {
            return _bIsSessionScope;
         }

         public boolean isRequestScope()
         {
            return _bIsRequestScope;
         }

         public boolean isInitOnStartUp()
         {
            return _bIsInitOnStartUp;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            return
               XML.Config_ModelDefs_ModelDef
                  .toXML( XML.Config_ModelDefs_ModelDef_Class.toXML( _sModelClassName )
                          + XML.Config_ModelDefs_ModelDef_FactoryClass.toXML( _sModelFactoryClassName )
                          + XML.Config_ModelDefs_ModelDef_Scope.toXML( _bIsApplicationScope
                                                                       ? "Application"
                                                                       : ( _bIsSessionScope
                                                                           ? "Session"
                                                                           : ( _bIsRequestScope
                                                                               ? "Request"
                                                                               : "!! invalid value !!"
                                                                             )
                                                                         )
                                                                     )
                          + XML.Config_ModelDefs_ModelDef_InitOnStartUp.toXML( _bIsInitOnStartUp )
                        );
         }
      }
   }

   /**
    * Modular configuration container
    */
   public static class Templating
   {
      private TemplatePath             _oTemplatePath       = new TemplatePath();
      private Locale                   _oLocale;
      private TemplatingEngine         _oTemplatingEngine   = new TemplatingEngine();
      private boolean                  _bExposePublicFields;

      public TemplatePath getTemplatePath()
      {
         return _oTemplatePath;
      }

      public TemplatingEngine getTemplatingEngine()
      {
         return _oTemplatingEngine;
      }

      public Locale getLocale()
      {
         return _oLocale;
      }

      public void setLocale( Locale oLocale )
      {
         _oLocale = oLocale;
      }

      public boolean isExposePublicFields()
      {
         return _bExposePublicFields;
      }

      public void setExposePublicFields( boolean bExposePublicFields )
      {
         _bExposePublicFields = bExposePublicFields;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         String   sXML_TemplatePath             =  _oTemplatePath.toXML();
         String   sXML_TemplatingEngineProvider =  _oTemplatingEngine.toXML();
         String   sXML_Locale                   =  toXML_Locale();

         if ( sXML_TemplatePath.equals( "" )
               && sXML_Locale.equals( "" )
               && sXML_TemplatingEngineProvider.equals( "" )
            )
         {
            return "";
         }
         else
         {
            StringBuffer   oBuffer = new StringBuffer();

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_Templating.OPEN_IND );

            oBuffer.append( sXML_TemplatePath );
            oBuffer.append( sXML_Locale );
            oBuffer.append( sXML_TemplatingEngineProvider );

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_Templating.CLOSE_IND );

            return  oBuffer.toString();
         }
      }

      private String toXML_Locale()
      {
         if ( _oLocale == null )
         {
            return "";
         }
         else
         {
            return
               XML.Config_Templating_Locale
                  .toXML( XML.Config_Templating_Locale_ISOLanguage.toXML( _oLocale.getLanguage() )
                          + XML.Config_Templating_Locale_ISOCountry.toXML( _oLocale.getCountry() )
                        );
         }
      }

      /**
       * Modular configuration container
       */
      public static class TemplatingEngine
      {
         private String    _sClassName;

         /**
          * Used to set a fully qualified class name that implements the templating engine interface
          * (the templating engine interface is com.acciente.induction.template.TemplatingEngine)
          *
          * @param sClassName a string representing fully qualified classname or null to use the default templating engine
          */
         public void setClassName( String sClassName )
         {
            _sClassName = sClassName;
         }

         /**
          * Returns the name of the templating engine class configured or null if no value was provided
          *
          * @return a string representing a fully qualified class name
          */
         public String getClassName()
         {
            return _sClassName;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            if ( _sClassName == null )
            {
               return "";
            }
            else
            {
               return
                  XML.Config_Templating_TemplatingEngine
                     .toXML( XML.Config_Templating_TemplatingEngine_Class.toXML( _sClassName ) );
            }
         }
      }

      /**
       * Modular configuration container
       */
      public static class TemplatePath
      {
         private  List _oTemplatePath = new ArrayList();

         /**
          * Adds a directory to the list of locations in which a template will be searched
          *
          * @param oDir a File object representing a directory
          */
         public void addDir( File oDir )
         {
            _oTemplatePath.add( new Dir( oDir ) );
         }

         /**
          * Adds a class to the list of locations in which a template will be searched,
          * this setting will only be used by a templating engine that supports it (e.g: Freemarker)
          * in these cases the templating engine will call the getResource() method on the specified
          * class to retrieve a template
          *
          * @param sLoaderClassName a prefix to append to the template name before attempting
          * @param sPath a path to where the templates are located, if the path starts with a /
          * then the path is absolute, otherwise it is assumed to be relative to the package name
          * of the loader class
          */
         public void addLoaderClass( String sLoaderClassName, String sPath )
         {
            _oTemplatePath.add( new LoaderClass( sLoaderClassName, sPath ) );
         }

         /**
          * Adds a web application path to the list of locations in which a template will be searched,
          * this setting will only be used by a templating engine that supports it (e.g: Freemarker)
          * in these cases the templating engine will call the Servlet context's getResource() method
          *
          * to passing the name to getResource()
          * @param sRelativePath a path of the web application root (the parent of the WEB-INF folder)
          */
         public void addWebappPath( String sRelativePath )
         {
            _oTemplatePath.add( new WebappPath( sRelativePath ) );
         }

         /**
          * Returns the list of items added to this path
          * @return a list, each element in the list is one of the following types:
          * Dir, Class or WebappPath
          */
         public List getList()
         {
            return _oTemplatePath;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            if ( _oTemplatePath.size() == 0 )
            {
               return "";
            }
            else
            {
               StringBuffer   oBuffer = new StringBuffer();

               oBuffer.append( "\n" );
               oBuffer.append( XML.Config_Templating_TemplatePath.OPEN_IND );

               for ( Iterator oIter = _oTemplatePath.iterator(); oIter.hasNext(); )
               {
                  Object oPathItem = oIter.next();

                  if ( oPathItem instanceof Dir )
                  {
                     oBuffer.append ( ( ( Dir ) oPathItem ).toXML() );
                  }
                  else if ( oPathItem instanceof LoaderClass )
                  {
                     oBuffer.append ( ( ( LoaderClass ) oPathItem ).toXML() );
                  }
                  else if ( oPathItem instanceof WebappPath )
                  {
                     oBuffer.append ( ( ( WebappPath ) oPathItem ).toXML() );
                  }
                  else
                  {
                     throw new IllegalArgumentException( "config-error: internal error: unknown template path item : " + oPathItem + ", of type: " + oPathItem.getClass() );
                  }
               }

               oBuffer.append( "\n" );
               oBuffer.append( XML.Config_Templating_TemplatePath.CLOSE_IND );

               return oBuffer.toString();
            }
         }

         /**
          * Modular configuration container
          */
         public static class Dir
         {
            private  File     _oDir;

            private Dir( File oDir )
            {
               _oDir = oDir;
            }

            public File getDir()
            {
               return _oDir;
            }

            public String toString()
            {
               return toXML();
            }

            public String toXML()
            {
               return
                  XML.Config_Templating_TemplatePath_Directory.toXML( _oDir );
            }
         }

         /**
          * Modular configuration container
          */
         public static class LoaderClass
         {
            private  String _sLoaderClassName;
            private  String _sPath;

            private LoaderClass( String sLoaderClassName, String sPath )
            {
               _sLoaderClassName = sLoaderClassName;
               _sPath            = sPath;
            }

            public String getLoaderClassName()
            {
               return _sLoaderClassName;
            }

            public String getPath()
            {
               return _sPath;
            }

            public String toString()
            {
               return toXML();
            }

            public String toXML()
            {
               return
                  XML.Config_Templating_TemplatePath_LoaderClass
                     .toXML( XML.Config_Templating_TemplatePath_LoaderClass_Class.toXML( _sLoaderClassName )
                             + XML.Config_Templating_TemplatePath_LoaderClass_Path.toXML( _sPath )
                           );
            }
         }

         /**
          * Modular configuration container
          */
         public static class WebappPath
         {
            private String _sPath;

            public WebappPath( String sPath )
            {
               _sPath = sPath;
            }

            /**
             * This path is relative to the parent of the WEB-INF directory of the web application
             * @return a string path
             */
            public String getPath()
            {
               return _sPath;
            }

            public String toString()
            {
               return toXML();
            }

            public String toXML()
            {
               return
                  XML.Config_Templating_TemplatePath_WebAppPath.toXML( _sPath );
            }
         }
      }
   }

   /**
    * Modular configuration container
    */
   public static class ControllerMapping
   {
      private List      _oURLToClassMapList        = new ArrayList();
      private List      _oErrorToClassMapList      = new ArrayList();
      private String    _sDefaultHandlerMethodName = "handler";
      private boolean   _bIgnoreMethodNameCase     = false;

      public URLToClassMap addURLToClassMap( Pattern oURLPattern, String[] asClassPackages, Pattern oClassPattern )
      {
         URLToClassMap oURLToClassMap = new URLToClassMap( oURLPattern, asClassPackages, oClassPattern );

         _oURLToClassMapList.add( oURLToClassMap );

         return oURLToClassMap;
      }

      public List getURLToClassMapList()
      {
         return _oURLToClassMapList;
      }

      public ErrorToClassMap addErrorToClassMap( String sClassName, String sClassMethodName )
      {
         ErrorToClassMap oErrorToClassMap = new ErrorToClassMap( sClassName, sClassMethodName );

         _oErrorToClassMapList.add( oErrorToClassMap );

         return oErrorToClassMap;
      }

      public List getErrorToClassMapList()
      {
         return _oErrorToClassMapList;
      }

      public String getDefaultHandlerMethodName()
      {
         return _sDefaultHandlerMethodName;
      }

      public void setDefaultHandlerMethodName( String sDefaultHandlerMethodName )
      {
         if ( sDefaultHandlerMethodName == null )
         {
            throw new IllegalArgumentException( "config-error: default handler name cannot be set to null or empty" );
         }

         if ( ! Character.isJavaIdentifierStart( sDefaultHandlerMethodName.charAt( 0 )) )
         {
            throw new IllegalArgumentException( "config-error: default handler name starts with an invalid character" );
         }

         _sDefaultHandlerMethodName = sDefaultHandlerMethodName;
      }

      public boolean isIgnoreMethodNameCase()
      {
         return _bIgnoreMethodNameCase;
      }

      /**
       * Controls if the default resolver should respect method case when looking for a method. A resolver
       * implementation is may choose to ignore this setting.
       *
       * @param bIgnoreMethodNameCase if true is specified, it tells the resolver implementation to
       * search for a handler method name ignoring case.
       */
      public void setIgnoreMethodNameCase( boolean bIgnoreMethodNameCase )
      {
         _bIgnoreMethodNameCase = bIgnoreMethodNameCase;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         StringBuffer   oBuffer = new StringBuffer();

         oBuffer.append( "\n" );
         oBuffer.append( XML.Config_ControllerMapping.OPEN_IND );

         for ( Iterator oIter = _oURLToClassMapList.iterator(); oIter.hasNext(); )
         {
            oBuffer.append ( ( ( URLToClassMap ) oIter.next() ).toXML() );
         }

         for ( Iterator oIter = _oErrorToClassMapList.iterator(); oIter.hasNext(); )
         {
            oBuffer.append ( ( ( ErrorToClassMap ) oIter.next() ).toXML() );
         }

         oBuffer.append( XML.Config_ControllerMapping_DefaultHandlerMethod.toXML( _sDefaultHandlerMethodName ) );
         oBuffer.append( XML.Config_ControllerMapping_IgnoreHandlerMethodCase.toXML( _bIgnoreMethodNameCase ) );

         oBuffer.append( "\n" );
         oBuffer.append( XML.Config_ControllerMapping.CLOSE_IND );

         return oBuffer.toString();
      }

      /**
       * Modular configuration container
       */
      public static class URLToClassMap
      {
         private String[]  _asClassPackages;
         private Pattern   _oURLPattern;
         private Pattern   _oClassPattern;
         private List      _aoFindReplaceDirectives = new ArrayList();

         private URLToClassMap( Pattern oURLPattern, String[] asClassPackages, Pattern oClassPattern )
         {
            validateURLPattern( oURLPattern );
            validateClassPackages( asClassPackages );
            validateClassPattern( oClassPattern );

            _oURLPattern      = oURLPattern;
            _asClassPackages  = asClassPackages;
            _oClassPattern    = oClassPattern;
         }

         public Pattern getURLPattern()
         {
            return _oURLPattern;
         }

         public String[] getClassPackages()
         {
            return _asClassPackages;
         }

         public Pattern getClassPattern()
         {
            return _oClassPattern;
         }

         public void addClassFindReplaceDirective( String sFindStr, String sReplaceStr )
         {
            _aoFindReplaceDirectives.add( new FindReplaceDirective( sFindStr, sReplaceStr ) );
         }

         public List getClassFindReplaceDirectives()
         {
            return _aoFindReplaceDirectives;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            StringBuffer   oBuffer = new StringBuffer();

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_ControllerMapping_URLToClassMap.OPEN_IND );

            oBuffer.append( XML.Config_ControllerMapping_URLToClassMap_URLPattern.toXML( _oURLPattern ) );
            oBuffer.append( XML.Config_ControllerMapping_URLToClassMap_ClassPackages.toXML( Arrays.asList( _asClassPackages ) ) );
            oBuffer.append( XML.Config_ControllerMapping_URLToClassMap_ClassPattern.toXML( _oClassPattern ) );

            for ( Iterator oIter = _aoFindReplaceDirectives.iterator(); oIter.hasNext(); )
            {
               oBuffer.append ( ( ( FindReplaceDirective ) oIter.next() ).toXML() );
            }

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_ControllerMapping_URLToClassMap.CLOSE_IND );

            return oBuffer.toString();
         }

         public static class FindReplaceDirective
         {
            private  String   _sFindStr;
            private  String   _sReplaceStr;

            private FindReplaceDirective( String sFindStr, String sReplaceStr )
            {
               if ( sFindStr == null )
               {
                  throw new IllegalArgumentException( "config-error: find string cannot be null!" );
               }

               if ( sReplaceStr == null )
               {
                  throw new IllegalArgumentException( "config-error: replace string cannot be null!" );
               }

               _sFindStr      = sFindStr;
               _sReplaceStr   = sReplaceStr;
            }

            public String getFindString()
            {
               return _sFindStr;
            }

            public String getReplaceString()
            {
               return _sReplaceStr;
            }

            public String toString()
            {
               return toXML();
            }

            public String toXML()
            {
               return
                  XML.Config_ControllerMapping_URLToClassMap_ClassReplace
                     .toXML( XML.Config_ControllerMapping_URLToClassMap_ClassReplace_Find.toXML( _sFindStr )
                             + XML.Config_ControllerMapping_URLToClassMap_ClassReplace_Replace.toXML( _sReplaceStr )
                           );
            }
         }
      }

      /**
       * Modular configuration container
       */
      public static class ErrorToClassMap
      {
         private ExceptionPattern   _oExceptionPattern = new ExceptionPattern( "java.lang.Throwable", true );
         private String             _sClassName;
         private String             _sClassMethodName;

         public ErrorToClassMap( String sClassName, String sClassMethodName )
         {
            if ( sClassName == null )
            {
               throw new IllegalArgumentException( "config-error: controller class name cannot be empty" );
            }

            _sClassName       = sClassName;
            _sClassMethodName = sClassMethodName;
         }

         public void setExceptionPattern( String sClassName, boolean bIncludeDerived )
         {
            if ( sClassName == null )
            {
               throw new IllegalArgumentException( "config-error: exception pattern class name cannot be empty" );
            }

            _oExceptionPattern = new ExceptionPattern( sClassName, bIncludeDerived );
         }

         public ExceptionPattern getExceptionPattern()
         {
            return _oExceptionPattern;
         }

         public String getClassName()
         {
            return _sClassName;
         }

         public String getClassMethodName()
         {
            return _sClassMethodName;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            return
               XML.Config_ControllerMapping_ErrorToClassMap
                  .toXML( _oExceptionPattern.toXML()
                          + XML.Config_ControllerMapping_ErrorToClassMap_ClassName.toXML( _sClassName )
                        );
         }

         public static class ExceptionPattern
         {
            private String    _sClassname;
            private boolean   _bIncludeDerived;

            public ExceptionPattern( String sClassname, boolean bIncludeDerived )
            {
               _sClassname       = sClassname;
               _bIncludeDerived  = bIncludeDerived;
            }

            public String getClassName()
            {
               return _sClassname;
            }

            public boolean isIncludeDerived()
            {
               return _bIncludeDerived;
            }

            public String toString()
            {
               return toXML();
            }

            public String toXML()
            {
               return
                  XML.Config_ControllerMapping_ErrorToClassMap_ExceptionPattern
                     .toXML( XML.Config_ControllerMapping_ErrorToClassMap_ExceptionPattern_ClassName.toXML( _sClassname )
                             + XML.Config_ControllerMapping_ErrorToClassMap_ExceptionPattern_IncludeDerived.toXML( _bIncludeDerived )
                           );
            }
         }
      }
   }

   /**
    * Modular configuration container
    */
   public static class ViewMapping
   {
      private List      _oURLToClassMapList        = new ArrayList();

      public URLToClassMap addURLToClassMap( Pattern oURLPattern, String[] asClassPackages, Pattern oClassPattern )
      {
         URLToClassMap oURLToClassMap = new URLToClassMap( oURLPattern, asClassPackages, oClassPattern );

         _oURLToClassMapList.add( oURLToClassMap );

         return oURLToClassMap;
      }

      public List getURLToClassMapList()
      {
         return _oURLToClassMapList;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         StringBuffer   oBuffer = new StringBuffer();

         oBuffer.append( "\n" );
         oBuffer.append( XML.Config_ViewMapping.OPEN_IND );

         for ( Iterator oIter = _oURLToClassMapList.iterator(); oIter.hasNext(); )
         {
            oBuffer.append ( ( ( URLToClassMap ) oIter.next() ).toXML() );
         }

         oBuffer.append( "\n" );
         oBuffer.append( XML.Config_ViewMapping.CLOSE_IND );

         return oBuffer.toString();
      }

      /**
       * Modular configuration container
       */
      public static class URLToClassMap
      {
         private String[]  _asClassPackages;
         private Pattern   _oURLPattern;
         private Pattern   _oClassPattern;
         private List      _aoFindReplaceDirectives = new ArrayList();

         private URLToClassMap( Pattern oURLPattern, String[] asClassPackages, Pattern oClassPattern )
         {
            validateURLPattern( oURLPattern );
            validateClassPackages( asClassPackages );
            validateClassPattern( oClassPattern );

            _oURLPattern      = oURLPattern;
            _asClassPackages  = asClassPackages;
            _oClassPattern    = oClassPattern;
         }

         public Pattern getURLPattern()
         {
            return _oURLPattern;
         }

         public String[] getClassPackages()
         {
            return _asClassPackages;
         }

         public Pattern getClassPattern()
         {
            return _oClassPattern;
         }

         public void addClassFindReplaceDirective( String sFindStr, String sReplaceStr )
         {
            _aoFindReplaceDirectives.add( new FindReplaceDirective( sFindStr, sReplaceStr ) );
         }

         public List getClassFindReplaceDirectives()
         {
            return _aoFindReplaceDirectives;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            StringBuffer   oBuffer = new StringBuffer();

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_ViewMapping_URLToClassMap.OPEN_IND );

            oBuffer.append( XML.Config_ViewMapping_URLToClassMap_URLPattern.toXML( _oURLPattern ) );
            oBuffer.append( XML.Config_ViewMapping_URLToClassMap_ClassPackages.toXML( Arrays.asList( _asClassPackages ) ) );
            oBuffer.append( XML.Config_ViewMapping_URLToClassMap_ClassPattern.toXML( _oClassPattern ) );

            for ( Iterator oIter = _aoFindReplaceDirectives.iterator(); oIter.hasNext(); )
            {
               oBuffer.append ( ( ( FindReplaceDirective ) oIter.next() ).toXML() );
            }

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_ViewMapping_URLToClassMap.CLOSE_IND );

            return oBuffer.toString();
         }

         public static class FindReplaceDirective
         {
            private  String   _sFindStr;
            private  String   _sReplaceStr;

            private FindReplaceDirective( String sFindStr, String sReplaceStr )
            {
               if ( sFindStr == null )
               {
                  throw new IllegalArgumentException( "config-error: find string cannot be null!" );
               }

               if ( sReplaceStr == null )
               {
                  throw new IllegalArgumentException( "config-error: replace string cannot be null!" );
               }

               _sFindStr      = sFindStr;
               _sReplaceStr   = sReplaceStr;
            }

            public String getFindString()
            {
               return _sFindStr;
            }

            public String getReplaceString()
            {
               return _sReplaceStr;
            }

            public String toString()
            {
               return toXML();
            }

            public String toXML()
            {
               return
                  XML.Config_ViewMapping_URLToClassMap_ClassReplace
                     .toXML( XML.Config_ViewMapping_URLToClassMap_ClassReplace_Find.toXML( _sFindStr )
                             + XML.Config_ViewMapping_URLToClassMap_ClassReplace_Replace.toXML( _sReplaceStr )
                           );
            }
         }
      }
   }

   /**
    * Modular configuration container
    */
   public static class RedirectMapping
   {
      private List   _oClassToURLMapList = new ArrayList();
      private String _sURLBase           = "";

      public ClassToURLMap addClassToURLMap( String[] asClassPackages, Pattern oClassPattern, String sURLFormat, String sAlternateURLFormat )
      {
         ClassToURLMap oClassToURLMap = new ClassToURLMap( asClassPackages, oClassPattern, sURLFormat, sAlternateURLFormat );

         _oClassToURLMapList.add( oClassToURLMap );

         return oClassToURLMap;
      }

      public List getClassToURLMapList()
      {
         return _oClassToURLMapList;
      }

      public String getURLBase()
      {
         return _sURLBase;
      }

      public void setURLBase( String sURLBase )
      {
         _sURLBase = sURLBase;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         if ( _oClassToURLMapList.size() == 0 && Strings.isEmpty( _sURLBase ) )
         {
            return "";
         }

         StringBuffer   oBuffer = new StringBuffer();

         oBuffer.append( "\n" );
         oBuffer.append( XML.Config_RedirectMapping.OPEN_IND );

         for ( Iterator oIter = _oClassToURLMapList.iterator(); oIter.hasNext(); )
         {
            oBuffer.append ( ( ( ClassToURLMap ) oIter.next() ).toXML() );
         }

         if ( ! Strings.isEmpty( _sURLBase ) )
         {
            oBuffer.append( XML.Config_RedirectMapping_URLBase.toXML( _sURLBase ) );
         }

         oBuffer.append( "\n" );
         oBuffer.append( XML.Config_RedirectMapping.CLOSE_IND );

         return oBuffer.toString();
      }

      /**
       * Modular configuration container
       */
      public static class ClassToURLMap
      {
         public static final String    SHORTNAME_LITERAL       = "$Name";
         public static final String    METHODNAME_LITERAL      = "$Method";

         public static final String    SHORTNAME_SEARCH_REGEX  = "\\$Name";
         public static final String    METHODNAME_SEARCH_REGEX = "\\$Method";

         private String[]     _asClassPackages;
         private Pattern      _oClassPattern;
         private String       _sURLFormat;
         private String       _sAlternateURLFormat;
         private List         _aoFindReplaceDirectives = new ArrayList();

         private ClassToURLMap( String[] asClassPackages, Pattern oClassPattern, String sURLFormat, String sAlternateURLFormat )
         {
            validateClassPattern( oClassPattern );

            if ( sURLFormat.indexOf( SHORTNAME_LITERAL ) == -1 )
            {
               throw new IllegalArgumentException( "config-error: URL format must use: "
                                                   + SHORTNAME_LITERAL
                                                   + " may optionally use: "
                                                   + METHODNAME_LITERAL );
            }

            // check the optional alternate URL format, if present
            if ( sAlternateURLFormat != null
                 && ( sAlternateURLFormat.indexOf( SHORTNAME_LITERAL ) == -1
                      || sAlternateURLFormat.indexOf( METHODNAME_LITERAL ) == -1 ) )
            {
               throw new IllegalArgumentException( "config-error: Alternate URL format must use both: "
                                                   + SHORTNAME_LITERAL
                                                   + " and: "
                                                   + METHODNAME_LITERAL );
            }

            _asClassPackages     = asClassPackages;
            _oClassPattern       = oClassPattern;
            _sURLFormat          = sURLFormat;
            _sAlternateURLFormat = sAlternateURLFormat;
         }

         public String[] getClassPackages()
         {
            return _asClassPackages;
         }

         public Pattern getClassPattern()
         {
            return _oClassPattern;
         }

         public String getURLFormat()
         {
            return _sURLFormat;
         }

         public String getAlternateURLFormat()
         {
            return _sAlternateURLFormat;
         }

         public void addClassFindReplaceDirective( String sFindStr, String sReplaceStr )
         {
            _aoFindReplaceDirectives.add( new FindReplaceDirective( sFindStr, sReplaceStr ) );
         }

         public List getClassFindReplaceDirectives()
         {
            return _aoFindReplaceDirectives;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            StringBuffer   oBuffer = new StringBuffer();

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_RedirectMapping_ClassToURLMap.OPEN_IND );

            oBuffer.append( XML.Config_RedirectMapping_ClassToURLMap_ClassPackages.toXML( _asClassPackages ) );
            oBuffer.append( XML.Config_RedirectMapping_ClassToURLMap_ClassPattern.toXML( _oClassPattern ) );
            oBuffer.append( XML.Config_RedirectMapping_ClassToURLMap_URLFormat.toXML( _sURLFormat ) );
            oBuffer.append( XML.Config_RedirectMapping_ClassToURLMap_URLFormatAlt.toXML( _sAlternateURLFormat ) );

            for ( Iterator oIter = _aoFindReplaceDirectives.iterator(); oIter.hasNext(); )
            {
               oBuffer.append ( ( ( FindReplaceDirective ) oIter.next() ).toXML() );
            }

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_RedirectMapping_ClassToURLMap.CLOSE_IND );

            return oBuffer.toString();
         }

         public static class FindReplaceDirective
         {
            private  String   _sFindStr;
            private  String   _sReplaceStr;

            private FindReplaceDirective( String sFindStr, String sReplaceStr )
            {
               if ( sFindStr == null )
               {
                  throw new IllegalArgumentException( "config-error: find string cannot be null!" );
               }

               if ( sReplaceStr == null )
               {
                  throw new IllegalArgumentException( "config-error: replace string cannot be null!" );
               }

               _sFindStr      = sFindStr;
               _sReplaceStr   = sReplaceStr;
            }

            public String getFindString()
            {
               return _sFindStr;
            }

            public String getReplaceString()
            {
               return _sReplaceStr;
            }

            public String toString()
            {
               return toXML();
            }

            public String toXML()
            {
               return
                  XML.Config_RedirectMapping_ClassToURLMap_ClassReplace
                     .toXML( XML.Config_RedirectMapping_ClassToURLMap_ClassReplace_Find.toXML( _sFindStr )
                             + XML.Config_RedirectMapping_ClassToURLMap_ClassReplace_Replace.toXML( _sReplaceStr )
                           );
            }
         }
      }
   }

   /**
    * Modular configuration container
    */
   public static class RequestInterceptors
   {
      private List _oRequestInterceptorList = new ArrayList();

      public void addRequestInterceptor( String sClassname )
      {
         _oRequestInterceptorList.add( new RequestInterceptor( sClassname ) );
      }

      public List getRequestInterceptorList()
      {
         return _oRequestInterceptorList;
      }

      public String toXML()
      {
         if ( _oRequestInterceptorList.size() == 0 )
         {
            return "";
         }
         else
         {
            StringBuffer   oBuffer = new StringBuffer();

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_RequestInterceptors.OPEN_IND );

            for ( Iterator oIter = _oRequestInterceptorList.iterator(); oIter.hasNext(); )
            {
               RequestInterceptor oRequestInterceptor = ( RequestInterceptor ) oIter.next();

               oBuffer.append ( oRequestInterceptor.toXML() );
            }

            oBuffer.append( "\n" );
            oBuffer.append( XML.Config_RequestInterceptors.CLOSE_IND );

            return oBuffer.toString();
         }
      }

      public static class RequestInterceptor
      {
         private String    _sClassName;

         /**
          * Creates a new request interceptor
          *
          * @param sClassName a fully qualified class name used to intercept an HTTP request
          */
         private RequestInterceptor( String sClassName )
         {
            _sClassName = sClassName;
         }

         /**
          * Returns the name of the class used to intercept an HTTP request
          *
          * @return a string representing a fully qualified class name
          */
         public String getClassName()
         {
            return _sClassName;
         }

         public String toString()
         {
            return toXML();
         }

         public String toXML()
         {
            return
               XML.Config_RequestInterceptor
                  .toXML( XML.Config_RequestInterceptor_Class.toXML( _sClassName ) );
         }
      }
   }

   /**
    * Modular configuration container
    */
   public static class ControllerResolver
   {
      private String    _sClassName;

      /**
       * Used to set a fully qualified class name used to resolve a HTTP request to a controller name (and method).
       * This parameter is usually not set. When not set, a default controller resolver that maps a URL path to
       * a controller name and method is used.
       *
       * @param sClassName a string representing fully qualified classname or null to use the default resolver
       */
      public void setClassName( String sClassName )
      {
         _sClassName = sClassName;
      }

      /**
       * Returns the name of the class used to resolve a HTTP request to a controller name (and method).
       *
       * @return a string representing a fully qualified class name
       */
      public String getClassName()
      {
         return _sClassName;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         return
            XML.Config_ControllerResolver
               .toXML( XML.Config_ControllerResolver_Class.toXML( _sClassName ) );
      }
   }

   /**
    * Modular configuration container
    */
   public static class ViewResolver
   {
      private String    _sClassName;

      /**
       * Used to set a fully qualified class name used to resolve a HTTP request to a view name (and method).
       * This parameter is usually not set. When not set, a default view resolver that maps a URL path to
       * a view name and method is used.
       *
       * @param sClassName a string representing fully qualified classname or null to use the default resolver
       */
      public void setClassName( String sClassName )
      {
         _sClassName = sClassName;
      }

      /**
       * Returns the name of the class used to resolve a HTTP request to a view name (and method).
       *
       * @return a string representing a fully qualified class name
       */
      public String getClassName()
      {
         return _sClassName;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         return
            XML.Config_ViewResolver
               .toXML( XML.Config_ViewResolver_Class.toXML( _sClassName ) );
      }
   }

   /**
    * Modular configuration container
    */
   public static class RedirectResolver
   {
      private String    _sClassName;

      /**
       * Used to set a fully qualified class name used to resolve a redirect request.
       *
       * @param sClassName a string representing fully qualified classname or null to use the default resolver
       */
      public void setClassName( String sClassName )
      {
         _sClassName = sClassName;
      }

      /**
       * Returns the name of the class used to resolve a redirect request.
       *
       * @return a string representing a fully qualified class name
       */
      public String getClassName()
      {
         return _sClassName;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         return
            XML.Config_RedirectResolver
               .toXML( XML.Config_RedirectResolver_Class.toXML( _sClassName ) );
      }
   }

   /**
    * Modular configuration container
    */
   public static class FileUpload
   {
      private static final int _1_KB = 1024;
      private static final int _1_MB = 1024 * 1024;

      private int    _iMaxUploadSizeInBytes              = 10 * _1_MB;  // by default files larger than 10 megabytes are not allowed
      private int    _iStoreFileOnDiskThresholdInBytes   = 10 * _1_KB;  // by default files larger than 10 kilobytes are stored on disk instead of memory
      private File   _oUploadedFileStorageDir            = null;

      public void setUploadedFileStorageDir( File oUploadedFileStorageDir )
      {
         _oUploadedFileStorageDir = oUploadedFileStorageDir;
      }

      /**
       * Returns the path to which uploaded files that are too large to be kept in memory
       * should be written, if no directory is specified all files are kept in memory
       *
       * @return a File object representing a path to which the uploaded files should be saved
       */
      public File getUploadedFileStorageDir()
      {
         return _oUploadedFileStorageDir;
      }

      public int getMaxUploadSizeInBytes()
      {
         return _iMaxUploadSizeInBytes;
      }

      /**
       * Sets the maximum upload file size in bytes will be accepted
       *
       * @param iMaxUploadSizeInBytes a file size in bytes
       */
      public void setMaxUploadSizeInBytes( int iMaxUploadSizeInBytes )
      {
         _iMaxUploadSizeInBytes = iMaxUploadSizeInBytes;
      }

      public int getStoreOnDiskThresholdInBytes()
      {
         return _iStoreFileOnDiskThresholdInBytes;
      }

      /**
       * Set a file size in bytes above which the uploaded file will be stored on disk
       *
       * @param iStoreOnDiskThresholdInBytes a file size in bytes
       */
      public void setStoreOnDiskThresholdInBytes( int iStoreOnDiskThresholdInBytes )
      {
         _iStoreFileOnDiskThresholdInBytes = iStoreOnDiskThresholdInBytes;
      }

      public String toString()
      {
         return toXML();
      }

      public String toXML()
      {
         return
            XML.Config_FileUpload
               .toXML( XML.Config_FileUpload_MaxUploadSize.toXML( _iMaxUploadSizeInBytes )
                       + XML.Config_FileUpload_StoreFileOnDiskThreshold.toXML( _iStoreFileOnDiskThresholdInBytes )
                       + XML.Config_FileUpload_UploadedFileStorageDir.toXML( _oUploadedFileStorageDir )
                     );
      }
   }

   private static void validateURLPattern( Pattern oURLPattern )
   {
      if ( oURLPattern == null )
      {
         throw new IllegalArgumentException( "config-error: url pattern cannot be empty!" );
      }

      // we validate the regex to ensure that it has atleast on matching group
      int iGroupCount = oURLPattern.matcher( "" ).groupCount();

      if ( iGroupCount != 1 && iGroupCount != 2 )
      {
         throw new IllegalArgumentException( "config-error: must have exactly 1, or exactly 2, matching group(s) in URL pattern: "
                                             + oURLPattern.pattern() );
      }
   }

   private static void validateClassPackages( String[] asClassPackages )
   {
      if ( asClassPackages == null )
      {
         throw new IllegalArgumentException( "config-error: package names(s) cannot be empty!" );
      }
   }

   private static void validateClassPattern( Pattern oClassPattern )
   {
      if ( oClassPattern == null )
      {
         throw new IllegalArgumentException( "config-error: class pattern cannot be empty!" );
      }

      // we validate the regex to ensure that it has atleast on matching group
      if ( oClassPattern.matcher( "" ).groupCount() != 1 )
      {
         throw new IllegalArgumentException( "config-error: must have exactly 1 matching group in class pattern: "
                                             + oClassPattern.pattern() );
      }
   }
}

// EOF