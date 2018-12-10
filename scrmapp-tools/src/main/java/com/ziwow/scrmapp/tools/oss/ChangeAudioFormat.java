package com.ziwow.scrmapp.tools.oss;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import java.io.File;

public class ChangeAudioFormat {

  public static String changeToMp3(String sourcePath, String targetPath) {
    File source = new File(sourcePath);
    File target = new File(targetPath);
    AudioAttributes audio = new AudioAttributes();
    Encoder encoder = new Encoder();

    audio.setCodec("libmp3lame");
    EncodingAttributes attrs = new EncodingAttributes();
    attrs.setFormat("mp3");
    attrs.setAudioAttributes(audio);

    try {
      encoder.encode(source, target, attrs);
      return targetPath;
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return null;
    } catch (InputFormatException e) {
      e.printStackTrace();
      return null;
    } catch (EncoderException e) {
      e.printStackTrace();
      return null;
    }
  }
}
