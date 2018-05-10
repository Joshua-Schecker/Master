import java.awt.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

// has public methods: opposite(), removeBlue(), removeGreen(), removeRed(),
// blur(int degree), and save(String filename)

class Pix
{
  //width
  int w=0;
  //height
  int h=0;
  // initialize instance image
  BufferedImage img=null;
  // initialize instance image name
  String imgname=null;
  //
  int r=0;
  int g=0;
  int b=0;
  int rgb=0;
  //constructor
  public Pix(String imagename)throws Exception
  {
    // assign input to instance variables
    img=ImageIO.read(new File(imagename));
    w = img.getWidth();
    h = img.getHeight();
    imgname=imagename;
  }
 // sets opposite rgb values for each pixel
 // example: opposite(255,0,127)=(0,255,128)
  public void opposite()
  {
    // initialize variables for loop operation
    int y=0;
    int x=0;
    int newrgb=0;

    for (x = 0; x < w; x++)// iterate through rows
    {
        for (y = 0; y < h; y++)// iterate through columns
        {
          // assign rgb int value of pixel(x,y) (current pixel)
          // parse rgb values from int
          parseRGB(x,y);
          // assign opposite values
          r=(r-255)*-1;
          b=(b-255)*-1;
          g=(g-255)*-1;
          // creates new color with opposite values to pixel(x,y) color
          newrgb = new Color(r, g, b).getRGB();
          // assigns new color to pixel(x,y)
          img.setRGB(x, y, newrgb);
        }
    }
  }
// sets red values to 0
  public void removeRed()
  {
    int y=0;
    int x=0;
    int newrgb=0;

    for (x = 0; x < w; x++)
    {
        for (y = 0; y < h; y++)
        {
          parseRGB(x,y);;
          r=0;
          // creates new color with same green, blue values and
          // red=0
          newrgb = new Color(r, g, b).getRGB();
          img.setRGB(x, y, newrgb);
        }
    }
  }
  // sets blue values to 0
  public void removeBlue()
  {
    int y=0;
    int x=0;
    int newrgb=0;

    for (x = 0; x < w; x++)
    {
        for (y = 0; y < h; y++)
        {
          parseRGB(x,y);
          b=0;
          // creates new color with same green, red values and
          // blue=0
          newrgb = new Color(r, g, b).getRGB();
          img.setRGB(x, y, newrgb);
        }
    }
  }
// sets green values to 0
  public void removeGreen()
  {
    int y=0;
    int x=0;
    int newrgb=0;

    for (x = 0; x < w; x++)
    {
        for (y = 0; y < h; y++)
        {
          parseRGB(x,y);
          g=0;
          // creates new color with same blue, red values and
          // green=0
          newrgb = new Color(r, g, b).getRGB();
          img.setRGB(x, y, newrgb);

        }
    }
  }

  public void blur(int degree)
  {
    int length=0;
    int border=0;
    if (degree==1)
    {
      length=9;
      border=1;
    }
    if (degree==2)
    {
      length=25;
      border=2;
    }
    if (degree==3)
    {
      length=49;
      border=3;
    }
    r=0;
    g=0;
    b=0;
    int avgR=0;
    int avgG=0;
    int avgB=0;
    int y=0;
    int x=0;
    int x1=0;
    int y1=0;
    int newrgb=0;
    int i=0;
    int[] rArray=new int[length];
    int[] gArray=new int[length];
    int[] bArray=new int[length];
    int count=0;


    for (x = border; x<w-border; x++)
    {
        for (y = border; y<h-border; y++)
        {
          x1=-border;
          while (x1<border+1)
          {
            y1=-border;
            while (y1<border+1)
            {
              parseRGB(x+x1, y+y1);
              rArray[i]=r;
              gArray[i]=g;
              bArray[i]=b;
              i++;
              y1++;
            }
            x1++;
          }
          avgR=average(rArray);
          avgG=average(gArray);
          avgB=average(bArray);
          newrgb = new Color(avgR, avgG, avgB).getRGB();
          img.setRGB(x, y, newrgb);
          i=0;
        }
    }
  }

  private int average(int[] input)
  {
    int sum=0;
    for (int j = 0; j<input.length; j++)
    {
      sum=sum+input[j];
    }
    int avg=sum/input.length;
    return avg;
  }

  private void parseRGB(int x, int y)
  {
    rgb=img.getRGB(x,y);
    r = (rgb >> 16) & 0xff;
    g = (rgb >> 8) & 0xff;
    b = rgb & 0xff;
  }


  // writes new .png file with parameter as filename and img as source
  public void save(String filename)throws Exception
  {
    File outputfile = new File(filename);
    ImageIO.write(img, "PNG", outputfile);
  }
}
