import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
public class Budget
{
    Scanner sc = new Scanner(System.in);
    Calendar c = Calendar.getInstance();
    int yr,dt,mn;
    String datePath, alreadyWrittenText = "", monthDir = "";
    public static void main(String args[]) throws IOException
    {
        Budget b = new Budget();
        b.process();
    }   

    public void process() throws IOException
    {
        yr = c.get(Calendar.YEAR);
        dt = c.get(Calendar.DATE);
        mn = c.get(Calendar.MONTH) + 1;
        System.out.println(yr + "\n" + dt + "\n" + mn);
        datePath = "Month "+ mn + "\\" + Path(dt+"_"+mn+"_"+yr);
        monthDir = "Month " + mn;
        Path tempDirectory = Files.createTempDirectory(monthDir);
        if(Files.exists(tempDirectory))
        {
            File monthWise = new File(monthDir);
            monthWise.mkdir();
        }
        // try
        // {
        //     File dayWise = new File(datePath);
        //     
        //     if (dayWise.createNewFile()) 
        //     {
        //         System.out.println("File created: " + dayWise.getName());
        //     } 
        //     else 
        //     {
        //         System.out.println("File already exists.");
        //     }
        // } 
        // catch (IOException e) 
        // {
        //     System.out.println("An error occurred.");
        //     e.printStackTrace();
        // }
        amountUsedTracking(datePath);
    }


    public void amountUsedTracking(String path)
    {
        try 
        {
            File f1 = new File(path + ".txt");
            if(f1.exists())
            {
                alreadyWrittenText = copyPreviousData(f1);
                System.out.println(alreadyWrittenText);
            }
            FileWriter fw1 = new FileWriter(path + ".txt");
            if(f1.exists())
                fw1.write(alreadyWrittenText + "\n");

        
            File f2 = new File(monthDir + "\\DaywiseRemainingamountUsed.txt");
            if(f2.exists())
            {
                alreadyWrittenText = copyPreviousData(f2);
            }
            FileWriter fw2 = new FileWriter(f2.getAbsolutePath());
            if(f2.exists())
                fw2.write(alreadyWrittenText + "\n");
            String subject = "";
            String amount = "";
            float budget = AmountLeft(f2.getAbsolutePath());
            float amountUsed = 0.0f;
            if(dt == 1)
                fw1.write("amountUsed of this month is Rs.4000 \n");
            while(true)
            {
                System.out.println("Enter where did you spend your money");
                subject = sc.next();
                if(subject.equalsIgnoreCase("exit"))
                {
                    fw1.write("Available balance: " + amountUsed + "\n");
                    fw2.write("Available balance: " + amountUsed + "\n");
                    break;
                }
                System.out.println("How much did you spend?");
                amount = sc.next();
                if(amount.equalsIgnoreCase("exit"))
                {
                    fw1.write("Available balance: " + amountUsed + "\n");
                    fw2.write("Available balance: " + amountUsed + "\n");
                    break;
                }
                else
                {
                    float amt = Float.parseFloat(amount);
                    amountUsed -= amt;
                    fw1.write(subject + ": " + amt + "\n");
                    if(amountUsed<0)
                    {
                        System.out.println("You have exceeded the amount used for this month");
                        fw1.write("You have exceeded the amount used for this month \n\n");
                    }
                }
            }
            System.out.println("You are left with Rs." + amountUsed);
            fw1.close();
            fw2.close();
        }
        catch (IOException e) 
        {
            System.out.println();
        }
    }


    public float AmountLeft(String path) throws IOException
    {
        FileReader fr = new FileReader(path);
        System.out.println(path);
        Scanner read = new Scanner(fr);
        String temp = "";
        float budget = 4000.0f;
        int i = 0;
        for(i = 0; read.hasNextLine(); i++)
        {
            System.out.println("Inside Loop");
            temp = read.nextLine();
            budget -= Float.parseFloat(temp.substring(temp.indexOf("Available balance: ")).trim());
            System.out.println(Float.parseFloat(temp.substring(temp.indexOf("Available balance: ")).trim()));
        }
        System.out.println(i);
        fr.close();
        read.close();
        System.out.println(budget);
        return budget;
    }


    public String copyPreviousData(File f) throws IOException
    {
        FileReader fr = new FileReader(f.getAbsolutePath());
        Scanner storing = new Scanner(fr);
        alreadyWrittenText = "";
        for(int i = 0; storing.hasNextLine(); i++)
        {
            alreadyWrittenText += storing.nextLine() + "\n";
        }
        fr.close();
        storing.close();
        return alreadyWrittenText;
    }


    //returns the path of the file that can be read by the interpreter, i.e., changing the / to // and removing the ""
    public String Path(String filePath)
    {
        String filePathReadable = "";
        for(int i = 0; i<filePath.length(); i++)
        {
            if(filePath.charAt(i) == '"')
                continue;
            filePathReadable += filePath.charAt(i);
            if(filePath.charAt(i) == '\\')
                filePathReadable += "\\";
        }
        return filePathReadable;
    }
}