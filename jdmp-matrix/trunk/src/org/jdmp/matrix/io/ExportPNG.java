package org.jdmp.matrix.io;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public abstract class ExportPNG {

  private static final Logger logger = Logger.getLogger(ExportPNG.class
      .getName());



  public static final File selectFile() {
    return selectFile(null);
  }



  public static final File selectFile(Component c) {
    return FileSelector.selectFile(c, "PNG Files", ".png");
  }



  public static final void save(String file, Component c) {
    save(file, c, c.getWidth());
  }



  public static final void save(File file, Component c) {
    save(file, c, c.getWidth());
  }



  public static final void save(String file, Component c, int width) {
    save(new File(file), c, width);
  }



  public static final void save(File file, Component c, int width) {
    if (file == null) {
      logger.log(Level.WARNING, "no file selected");
      return;
    }
    if (c == null) {
      logger.log(Level.WARNING, "no component provided");
      return;
    }
    double factor = width / c.getWidth();
    BufferedImage image = new BufferedImage((int) (c.getWidth() * factor),
        (int) (c.getHeight() * factor), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = image.createGraphics();
    g2.scale(factor, factor);
    c.paint(g2);
    try {
      OutputStream out = new FileOutputStream(file);
      Iterator<?> writers = ImageIO.getImageWritersByFormatName("png");
      String[] s = ImageIO.getWriterFileSuffixes();
      ImageWriter writer = (ImageWriter) writers.next();
      ImageOutputStream ios = ImageIO.createImageOutputStream(file);
      writer.setOutput(ios);
      writer.write(image);
      out.close();
    } catch (Exception e) {
      logger.log(Level.WARNING, "could not save image", e);
    }
  }

}
