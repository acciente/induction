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
package com.acciente.induction.init.config.xmlconfigloader;

import com.acciente.induction.init.config.Config;
import com.acciente.induction.init.config.ConfigLoader;
import com.acciente.induction.init.config.ConfigLoaderException;
import org.xml.sax.SAXException;

import javax.servlet.ServletConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * An implementation of the ConfigLoader that loads the configuration from an
 * XML file.
 *
 * Log
 * Apr 26, 2008 APR  -  created
 */
// todo: support for a DTD validator to catch simple config errors, otherwise hard to trace
public class XMLConfigLoader implements ConfigLoader
{
   private  ServletConfig  _oServletConfig;
   private  File           _oConfigFile;

   public XMLConfigLoader( ServletConfig oServletConfig )
   {
      _oServletConfig = oServletConfig;
   }

   /**
    * This constructor was written to faciliate testing the config loader
    * by loading a file directly from the filesystem.
    *
    * @param oConfigFile
    */
   public XMLConfigLoader( File oConfigFile )
   {
      _oConfigFile = oConfigFile;
   }

   public Config getConfig() throws ConfigLoaderException
   {
      Config   oConfig;

      try
      {
         if ( _oServletConfig != null )
         {
            // first compute the expected name of config file name
            String   sConfigFileName = "/WEB-INF/induction-" + _oServletConfig.getServletName() + ".xml";

            InputStream oConfigStream;

            oConfigStream = _oServletConfig.getServletContext().getResourceAsStream( sConfigFileName );

            if ( oConfigStream == null )
            {
               throw new ConfigLoaderException( "config-load: error opening: " + sConfigFileName );
            }

            oConfig = readConfigFile( oConfigStream );
         }
         else if ( _oConfigFile != null )
         {
            oConfig = readConfigFile( _oConfigFile );
         }
         else
         {
            throw new ConfigLoaderException( "config-load: internal error, unrecognized config source" );
         }
      }
      catch ( IOException e )
      {
         throw new ConfigLoaderException( "config-load: I/O error", e );
      }
      catch ( SAXException e )
      {
         throw new ConfigLoaderException( "config-load: XML parse error", e );
      }

      return oConfig;
   }

   private Config readConfigFile( InputStream oConfigStream ) throws IOException, SAXException
   {
      Config   oConfig = new Config();

      DigesterFactory.getDigester( oConfig ).parse( oConfigStream );

      return oConfig;
   }

   private Config readConfigFile( File oConfigFile ) throws IOException, SAXException
   {
      Config   oConfig = new Config();

      DigesterFactory.getDigester( oConfig ).parse( oConfigFile );

      return oConfig;
   }
}

// EOF