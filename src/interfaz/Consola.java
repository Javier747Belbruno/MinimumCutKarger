package interfaz;

public class Consola
{
    public static String readLine()
    { 
       int ch;
       String r = "";
       boolean done = false;
       while (!done)
       {
	    try
	    {
    		ch = System.in.read();
    		if (ch < 0 || (char)ch == '\n') { done = true; }
    		else 
    		{
    		     if ((char)ch != '\r') { r = r + (char) ch; }
    		}
	    }
	    catch(java.io.IOException e)
	    {
    		done = true;
	    }
       }
       return r;
    }
  
    public static int readInt()    
    {
       while(true)
       { 
    	  try
    	  {
    	      return Integer.parseInt(readLine().trim());
    	  }
    	  catch(NumberFormatException e)
    	  {
    	      System.out.println("No es un integer. Por favor, pruebe otra vez!");
    	  }
       }
    }
  
    public static double readDouble()
    {
       while(true)
       { 
    	   try
    	   {
    	      return Double.parseDouble(readLine().trim());
    	   }
    	   catch(NumberFormatException e)
    	   {
    	      System.out.println("No es un flotante. " + "Por favor, pruebe otra vez!");
    	   }
       }
    }
}
