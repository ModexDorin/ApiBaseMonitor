
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dorin/workspace/java/ApiBaseCurrency/conf/routes
// @DATE:Mon Jun 25 01:14:46 EEST 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:3
package ro.iss.ApiBaseMonitor.controllers.javascript {

  // @LINE:3
  class ReverseExternalApiController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:3
    def requestId: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "ro.iss.ApiBaseMonitor.controllers.ExternalApiController.requestId",
      """
        function(requestId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Int]].javascriptUnbind + """)("requestId", requestId0))})
        }
      """
    )
  
  }


}
