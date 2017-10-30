import java.util.*;
import java.io.*;
import java.time.*;
import java.text.*;

class CalendarParse
{
  public static void main(String[] args)throws Exception
  {
    ArrayList<Event> events=populate();
    Collections.sort(events);
    toCSV(events);
    System.out.println(events.size());
  }

  static void toCSV(ArrayList<Event> list) throws Exception
  {
    PrintWriter pw=new PrintWriter("output.csv");
    pw.println("DATE"+","+"DURATION"+","+"PARTICIPANTS"+","+"SUMMARY");
    for(Event e: list){
      pw.println(e);
    }
    pw.close();
  }

  static ArrayList<Event> populate() throws Exception
  {
    ArrayList<Event> out=new ArrayList<Event>();
    Scanner sc=new Scanner(new File("joshs3.ics"));
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
  public void add(String s)
  {
    attendees+=" "+s;
  }
  @Override
  public String toString()
  {
      double difference = (endDate.getTime() - startDate.getTime())/3600000D;
      return date+","+String.valueOf(difference)+","+attendees+","+summary;

  }
  @Override
  public int compareTo(Event e)
  {
    return startDate.compareTo(e.startDate);
  }

}
