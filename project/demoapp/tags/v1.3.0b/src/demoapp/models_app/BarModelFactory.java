package demoapp.models_app;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

/**
 * This class is an Induction model factory. The only requirement is that this class have single
 * method named createModel().
 *
 * @created Jun 26, 2008
 *
 * @author Adinath Raveendra Raj
 */
public class BarModelFactory
{
   private  TarModel    _oTarModel;
   /**
    * Our createModel() is very simple, but you are free make it as fanciful as your
    * model instantiation demands. We declare the HttpServletRequest, ServletConfig
    * and TarModel parameters to demonstrate the injection of these values into the
    * createModel() method. HttpServletRequest, ServletConfig are "built-ins", while
    * TarModel is just another model class
    *
    * @return a BarModel object
    */
   public BarModel createModel( HttpServletRequest oRequest, ServletConfig oConfig, TarModel oTarModel )
   {
      System.out.println( "request=" + oRequest  );
      System.out.println( "config=" + oConfig  );
      System.out.println( "tar=" + oTarModel  );

      _oTarModel = oTarModel;

      return new BarModel( System.currentTimeMillis() );
   }
}
