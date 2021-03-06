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
package com.acciente.induction.dispatcher.model;

import com.acciente.commons.reflect.ParameterProviderException;
import com.acciente.induction.util.ConstructorNotFoundException;
import com.acciente.induction.util.ObjectFactory;

import javax.servlet.ServletConfig;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Internal.
 * This class manages instances of the the model factory classes configured in the system.
 *
 * @created Mar 25, 2008
 *
 * @author Adinath Raveendra Raj
 */
public class ConfiguredModelFactoryPool
{
   private Map             _oConfiguredModelFactoryMap;
   private ClassLoader     _oClassLoader;

   private ServletConfig   _oServletConfig;

   public ConfiguredModelFactoryPool( ClassLoader oClassLoader, ServletConfig oServletConfig )
   {
      _oClassLoader     = oClassLoader;
      _oServletConfig   = oServletConfig;

      _oConfiguredModelFactoryMap = new Hashtable(); // Hashtable for concurrency safety
   }

   public Object getConfiguredModelFactory( String sModelFactoryClassName )
      throws ClassNotFoundException, InvocationTargetException, ParameterProviderException, ConstructorNotFoundException, InstantiationException, IllegalAccessException
   {
      // first load the class (the class loader caches)
      Class oModelFactoryClass = _oClassLoader.loadClass( sModelFactoryClassName );

      // see if we have a cached instance of the factory class
      Object   oModelFactory  = _oConfiguredModelFactoryMap.get( oModelFactoryClass.getName() );

      if ( oModelFactory == null )
      {
         // no cached instance, create a new factory class instance and cache
         oModelFactory = createConfiguredModelFactory( oModelFactoryClass );

         _oConfiguredModelFactoryMap.put( oModelFactoryClass.getName(), oModelFactory );
      }
      else
      {
         if ( oModelFactory.getClass().hashCode() != oModelFactoryClass.hashCode() )
         {
            ObjectFactory.destroyObject( oModelFactory );

            // to be safe remove and add instead of overwrite to prevent leaving
            // an invalid obect in the cache in case creating the new instance
            // fails
            _oConfiguredModelFactoryMap.remove( oModelFactoryClass.getName() );

            // create a new factory class instance and cache
            oModelFactory = createConfiguredModelFactory( oModelFactoryClass );

            _oConfiguredModelFactoryMap.put( oModelFactoryClass.getName(), oModelFactory );
         }
      }

      return oModelFactory;
   }

   /**
    * Use to check if the specified model factory classname has been refined since it was last used by this pool
    *
    * @param sModelFactoryClassName the name of model factory to check
    * @return true if the model factory classname has been refined since it was last used by this pool
    * @throws ClassNotFoundException propagated exception
    */
   public boolean isConfiguredModelFactoryStale( String sModelFactoryClassName )
      throws ClassNotFoundException
   {
      // first load the class (the class loader caches)
      Class oModelFactoryClass = _oClassLoader.loadClass( sModelFactoryClassName );

      // see if we have a cached instance of the factory class
      Object   oModelFactory  = _oConfiguredModelFactoryMap.get( oModelFactoryClass.getName() );

      // ( oModelFactory == null ) is unexpected unless we dynamically reload model def configs so we consider it stale
      // if we encounter this situation, since it is more future safe to consider it stale in this case

      return ( ( oModelFactory == null ) || ( oModelFactory.getClass().hashCode() != oModelFactoryClass.hashCode() ) );
   }

   private Object createConfiguredModelFactory( Class oModelFactoryClass )
      throws InvocationTargetException, ConstructorNotFoundException, ParameterProviderException, IllegalAccessException, InstantiationException
   {
      return ObjectFactory.createObject( oModelFactoryClass, new Object[]{ _oServletConfig }, null );
   }
}

// EOF