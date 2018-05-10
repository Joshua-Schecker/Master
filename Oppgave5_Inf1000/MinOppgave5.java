// The Pix class takes an image as input and creates on object
// the methods of the class Pix apply image filters to the imported image
// the save method is used to write the modified image file
// this may take 10-20 seconds to run
// i have included baloons.png to test the program but you can substitute any .png file 



class MinOppgave5
{
  public static void main(String[] args)throws Exception
  {
  // initialize pix object and test opposite method
    Pix image1=new Pix("baloons.png");
    image1.opposite();
    image1.save("opposite.png");
    image1.opposite();// opposite of opposite = original
    image1.save("back_to_start.png");
// initialize pix object and chang blue values to 0
    Pix image2=new Pix("baloons.png");
    image2.removeBlue();
    image2.save("noBlue.png");
// initialize pix object and chang green values to 0
    Pix image3=new Pix("baloons.png");
    image3.removeGreen();
    image3.save("noGreen.png");
// initialize pix object and chang red values to 0
    Pix image4=new Pix("baloons.png");
    image4.removeRed();
    image4.save("noRed.png");
// initialize pix object and change all rgb values to 0 (black)
    Pix image5=new Pix("baloons.png");
    image5.removeBlue();
    image5.removeRed();
    image5.removeGreen();
    image5.save("Black.png");
// creates opposite of all black image
    image5.opposite();
    image5.save("White.png");
// initialize pix object and test blur method
// little blurry
    Pix image6=new Pix("baloons.png");
    image6.blur(1);
    image6.save("blur1.png");
// blurry
    Pix image7=new Pix("baloons.png");
    image7.blur(2);
    image7.save("blur2.png");
// most blurry
    Pix image8=new Pix("baloons.png");
    image8.blur(3);
    image8.save("blur3.png");
// even more blurry
    image8.blur(3);
    image8.blur(3);
    image8.blur(3);
    image8.save("blur4.png");
  }
}
