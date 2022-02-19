import java.util.HashMap;

import java.util.HashMap;

public class IdAndPassword {
    
            //id and pass   name                                     
    HashMap<String,String> Logininfo= new HashMap<String,String>();
    
    IdAndPassword(){ //CONSTRUCTOR
 
        Logininfo.put("bro","pizza"); //user id and pass
        Logininfo.put("brometheus","PASSWORD");
        Logininfo.put("brocode","abc123");
    }

   protected HashMap getLogininfo() { //its return type is hashmap
        return Logininfo;  //retruning our hashmap
    }

}
