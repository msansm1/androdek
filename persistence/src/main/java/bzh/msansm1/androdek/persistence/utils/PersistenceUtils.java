package bzh.msansm1.androdek.persistence.utils;

import java.util.Arrays;

/**
 * Created by ronan on 10/08/2016.
 */
public class PersistenceUtils {

    public static byte[] getPass(String pass){
        byte b = 0;
        byte[] key = new byte[64];
        Arrays.fill(key,b);

        byte [] bytes =  pass.getBytes();
        for(int i=0; i<bytes.length;i++){
            key[i]=bytes[i];
        }
        return key;
    }
}
