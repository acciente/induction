package com.acciente.dragonfly.init;

import com.acciente.commons.lang.Strings;
import com.acciente.dragonfly.init.config.Config;
import com.acciente.dragonfly.template.FreemarkerTemplatingEngine;
import com.acciente.dragonfly.template.TemplatingEngine;
import com.acciente.dragonfly.util.ConstructorNotFoundException;
import com.acciente.dragonfly.util.ObjectFactory;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * This class manages initialization of the templating engine based on the configured settings
 *
 * Log
 * Apr 23, 2008 APR  -  created
 */
public class TemplatingEngineInitializer
{
   public static TemplatingEngine getTemplatingEngine( Config.Templating oTemplatingConfig, ClassLoader oClassLoader, ServletConfig oServletConfig, Logger oLogger )
      throws IOException, ClassNotFoundException, InvocationTargetException, ConstructorNotFoundException, IllegalAccessException, InstantiationException
   {
      TemplatingEngine  oTemplatingEngine;

      if ( Strings.isEmpty( oTemplatingConfig.getTemplatingEngine().getClassName() ) )
      {
         // if no templating engine is configured use the freemarker engine as the default
         oTemplatingEngine = new FreemarkerTemplatingEngine( oTemplatingConfig, oClassLoader, oServletConfig, oLogger );
      }
      else
      {
         Class oTemplatingEngineClass = oClassLoader.loadClass( oTemplatingConfig.getTemplatingEngine().getClassName() );

         oTemplatingEngine
            =  ( TemplatingEngine )
               ObjectFactory.createObject( oTemplatingEngineClass,
                                           new Object[]{ oServletConfig,
                                                         oTemplatingConfig,
                                                         oClassLoader
                                                       }
                                         );
      }

      return oTemplatingEngine;
   }
}

// EOF