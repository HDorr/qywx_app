package com.ziwow.scrmapp.tools.utils;
/**
 * 
 * ClassName: SeedUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-7-16 上午9:57:30 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public class SeedUtil
{
    public static String newSeed ()
    {
        byte[] seed = new byte [16];
        for (int i = 0; i < seed.length; i++)
        {
            seed [i] = (byte) (Math.random () * 256 - 128);
        }
        return new sun.misc.BASE64Encoder ().encode (seed);
    }

    public static String encrypt (String seed, String password)
    {
        try
        {
            sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder ();
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder ();

            byte[] seedBytes = decoder.decodeBuffer (seed);
            byte[] pwdBytes = password.getBytes ("UTF-16LE");

            byte[] source = new byte [seedBytes.length + pwdBytes.length];
            System.arraycopy (seedBytes, 0, source, 0, seedBytes.length);
            System.arraycopy (pwdBytes, 0, source, seedBytes.length, pwdBytes.length);

            java.security.MessageDigest alga = java.security.MessageDigest.getInstance ("SHA1");
            alga.update (source);
            byte[] target = alga.digest ();

            return encoder.encode (target);
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}