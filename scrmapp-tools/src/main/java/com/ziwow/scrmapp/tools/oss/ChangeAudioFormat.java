package com.ziwow.scrmapp.tools.oss;

import it.sauronsoftware.jave.AudioUtils;
import java.io.File;

public class ChangeAudioFormat {

  public static void  changeToMp3(String sourcePath, String targetPath) {
    File source = new File(sourcePath);
    File target = new File(targetPath);
    AudioUtils.amrToMp3(source, target);
  }
}
