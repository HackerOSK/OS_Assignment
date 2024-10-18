import java.io.*;
import java.util.Arrays;

public class Practice {
    private char [][]M= new char[100][4];

    private char []buffer= new char[40];
    private char []R=new char[4];
    private char []IR=new char[4];
    private int IC;
    private int C;
    private int SI;

    private int M_used;
    private int data_card_skip=0;
    private String input_file;
    private String output_file;
    private FileReader input;
    private BufferedReader fread;
    private FileWriter output;
    private BufferedWriter fwrite;



    public Practice(String file,String output){
        this.input_file=file;
        this.SI=0;
        try {
            this.input = new FileReader(file);
            this.fread= new BufferedReader(input);
            this.output=new FileWriter(output);
            //this.fwrite= new BufferedWriter(this.output);
            //fwrite.write("Svsndjd");
            //fwrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LOAD()
    {
        int flag=0;
        String line;
        try {

            while((line=fread.readLine()) != null)
            {
                buffer=line.toCharArray();
                if(buffer[0]=='$'&& buffer[1]=='A'&&buffer[2]=='M'&& buffer[3]=='J') {
                    System.out.println("Program card detected");

                    continue;
                }
                else if(buffer[0]=='$'&& buffer[1]=='D'&&buffer[2]=='T'&& buffer[3]=='A')
                {
                    System.out.println("Data card detected");
//                    execute();
                    STARTEXECUTION();
                    flag=2;
                    continue;
                }
                else if(buffer[0]=='$'&& buffer[1]=='E'&&buffer[2]=='N'&& buffer[3]=='D')
                {
                    System.out.println("END card detected");
                    output.write("\n\n\n");
                    print_M();
                    continue;
                }
                if(M_used==100)
                {   //abort;
                    System.out.println("Abort due to exceed M usage");
                }
                // System.out.println(line.length());

                //System.out.println("Mai load hora");
                System.out.println("ur program starts here");
                for (int i = 0; i < line.length();) {
                    //System.out.println(buffer[i]);
                    M[M_used][i%4]=buffer[i];
                    i++;
                    if(i%4==0)
                        M_used++;
                }


            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(M_used);
        //print_M();
    }

    private void EXECUTEUSERPROGRAM(){
        while(1<2)
        {
            if(IC==100)
                break;
            IR[0]=M[IC][0];
            IR[1]=M[IC][1];
            IR[2]=M[IC][2];
            IR[3]=M[IC][3];

            IC++;
            if(IR[0]=='L' && IR[1]=='R')
            {
                String line = new String(IR);
                //System.out.println(line);
                //System.out.println(line.substring(2));
                int num=Integer.parseInt(line.substring(2));
                R[0]=M[num][0];
                R[1]=M[num][1];
                R[2]=M[num][2];
                R[3]=M[num][3];
            }
            else if(IR[0]=='S' && IR[1]=='R')
            {
                String line = new String(IR);
                int num=Integer.parseInt(line.substring(2));
                M[num][0]=R[0];
                M[num][1]=R[1];
                M[num][2]=R[2];
                M[num][3]=R[3];
            }
            else if(IR[0]=='C' && IR[1]=='R')
            {
                String line = new String(IR);
                int num=Integer.parseInt(line.substring(2));
                if(M[num][0]==R[0]&& M[num][1]==R[1]&& M[num][2]==R[2]&& M[num][3]==R[3])
                {
                    C=1;
                }
                else
                {
                    C=0;

                }
            }
            else if(IR[0]=='B' && IR[1]=='T')
            {
                if(C==1)
                {
                    String line = new String(IR);
                    int num=Integer.parseInt(line.substring(2));
                    IC=num;
                    C=0;
                }
            }
            else if(IR[0]=='G' && IR[1]=='D')
            {
                SI=1;
                MOS();
            }
            else if(IR[0]=='P' && IR[1]=='D')
            {
                SI=2;
                MOS();
            }
            else if(IR[0]=='H')
            {
                SI=3;
                MOS();
            }
        }
    }

    private void MOS() {
        int i=this.SI;
        if(i==1)
        {
            Read();
        }
        else if(i==2)
        {
            Write();
        }
        else if(i==3)
        {
            Terminate();
            
        }
        SI=0;
    }

    private void Terminate()
    {
        try {
            output.write("\n");
            output.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Write() {
        IR[3]='0';
        String line = new String(IR);
        int num=Integer.parseInt(line.substring(2));
        String t,total="";
        for(int i=0;i<10;i++)
        {

            t=new String(M[num+i]);
            total = total.concat(t);

        }
        System.out.println(total+"In write");
        try {
            output.write(total);
            output.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Read() {
        int flag=0;
        IR[3]='0';
        String line = new String(IR);

        int num=Integer.parseInt(line.substring(2));


        try {
            line=fread.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer=line.toCharArray();
        for (int i = 0; i < line.length();) {
            //System.out.println(buffer[i]);
            M[num][(i%4)]=buffer[i];
            i++;
            if(i%4==0)
                num++;
        }
    }

    public void STARTEXECUTION(){
        M_used=0;
        for(char arr[] : M)
        {
            Arrays.fill(arr,'\0');
        }
        C=0;
        IC=0;
        EXECUTEUSERPROGRAM();

    }
    public void print_M(){
        for(int i=0;i<100;i++) {
            System.out.println("M["+i+"] "+new String(M[i]));
        }
    }

    public static void main(String[] args) {
        Practice p1 = new Practice("O:\\OS\\Phase1\\input.txt","O:\\OS\\Phase1\\output.txt");
        p1.LOAD();
    }
}
