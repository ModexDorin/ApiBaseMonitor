
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dorin/workspace/java/ApiBaseCurrency/conf/routes
// @DATE:Mon Jun 25 01:14:46 EEST 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:3
package ro.iss.ApiBaseMonitor.controllers {

  // @LINE:3
  class ReverseExternalApiController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:3
    def requestId(requestId:Int): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Int]].unbind("requestId", requestId)))
    }
  
  }


}
