import java.util.*;
import java.io.*;
import java.time.*;
import java.text.*;

class CalendarParse
{
  /**
* This is the main method which makes use of addNum method.
* @param args path/filename of .ics file.
* @return Nothing.
* @exception Exception FileNotFound error.
* @see FileNotFoundException
*/
  public static void main(String[] args)throws Exception
  {
    ArrayList<Event> events=parse(args);
    Collections.sort(events);
    toCSV(events);
    System.out.println(events.size());
  }
  /**
  * This method is used to create a .csv output file.
  * @param list This is the list of parsed Events.
  * @return Nothing.
  */
  static void toCSV(ArrayList<Event> list) throws Exception
  {
    PrintWriter pw=new PrintWriter("output.csv");
    pw.println("DATE"+","+"DURATION"+","+"PARTICIPANTS"+","+"SUMMARY");
    for(Event e: list){
      pw.println(e);
    }
    pw.close();
  }
  /**
  * This method is used to create an ArrayList
  * of Events from an .ics file. Does not consider reminders.
  * @param args This is the path/name of the .ics file.
  * @return ArrayList<Event> This returns a list populated with Events.
  */
  static ArrayList<Event> parse(String[] args) throws Exception
  {
    ArrayList<Event> out=new ArrayList<Event>();
    Scanner sc=new Scanner(new File(args[0]));
    String owner="";
    String line;
    String st;
    String et;
    boolean go=false;
    Event temp;

    while(sc.hasNextLine())
    {
      line=sc.nextLine();
      if(line.startsWith("X-WR-CALNAME:"))
      {
        owner=line.substring(13);
      }
      if(line.equals("BEGIN:VEVENT")){
        go=true;
      }
      if(go)
      {
        if(line.startsWith("DTSTART:"))
        {
          st=line.substring(8);
          line=sc.nextLine();
          et=line.substring(6);
          temp=new Event(st, et, owner);
          out.add(temp);
          while(!line.equals("END:VEVENT"))
          {
            line=sc.nextLine();
            if(line.startsWith("ATTENDEE;"))
            {
              line=sc.nextLine();
              temp.add(line.substring(line.lastIndexOf(':')+1));
            }
            if(line.startsWith("SUMMARY:"))
            {
              if(line.length()!=9){
                temp.summary=line.substring(8);
              }
            }
          }
        }
      }
    }
    return out;
  }
}
class Event implements Comparable<Event>
{
  static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
  Date startDate=new Date();
  Date endDate=new Date();
  String owner;
  String attendees="";
  String summary=" ";
  String date;
  /**
  * This is the Constructor.
  * @param st start date.
  * @param et end date.
  * @param owner email of calendar owner.
  */
  public Event(String st, String et, String owner)
  {
    try
    {
      startDate=dateFormat.parse(st);
      endDate=dateFormat.parse(et);
    }catch (ParseException e){}
    this.date=String.valueOf(startDate.getDate())+"."+
              String.valueOf(startDate.getMonth()+1)+"."+
              String.valueOf(startDate.getYear()+1900);
    this.owner=owner;
  }
  /**
  * This method adds an email to the list of attendees.
  * @param s email.
  * @return Nothing.
  */
  public void add(String s)
  {
    attendees+=" "+s;
  }
  /**
  * This prints out the start date, end date, duration, summary
  * and attendees of this event.
  * @return Nothing.
  */
  @Override
  public String toString()
  {
      double difference = (endDate.getTime() - startDate.getTime())/3600000D;
      return date+","+String.valueOf(difference)+","+attendees+","+summary;

  }
  /**
  * This method is used to compare events based on earliest start date.
  * @return int -1 if less than, 0 if equal, 1 is greater than.
  */
  @Override
  public int compareTo(Event e)
  {
    return startDate.compareTo(e.startDate);
  }

}
