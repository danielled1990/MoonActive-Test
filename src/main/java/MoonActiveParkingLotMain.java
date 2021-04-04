import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * this is the main class that start the program.
 * right after the message "Welcome to Moon active parking lot!" you enter an imgur link
 * from the imgurPlates.txt file (for example: https://i.imgur.com/Atb2xiW.png)
 */
public class MoonActiveParkingLotMain {
    public static void main(String[] args) {
        while(true){
            try{

                System.out.println(String.format("Welcome to Moon active parking lot!"));

                InputStreamReader streamReader = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(streamReader);

                Log.getInstance().WriteToLogFile("Waiting for a car to show");
                /*reading the image*/
                String urlOfImage = bufferedReader.readLine().trim();

                Log.getInstance().WriteToLogFile("New car arrived");

                System.out.println("please wait while we procces your plate number....");
                /*giving the license plate number as text, check if the car can enter the praking lot, and bring the result*/
                IfCarCanEnterOrNot res = IfCarCanEnterOrNot.checkIfVihicleCanEnterByGivenCode
                        (new CheckIfLicensePlateIsValid().CheckLicenePlate
                                (new GetTextFromImage().getTextFromImageAPI(urlOfImage)));

                //printing the result to the costumer
                System.out.println(Log.getInstance().getlp()+" " +res.getResponse());

                //ENTER TO DB
                SQLHelper sqlSingelton = new SQLHelper();
                sqlSingelton.init();
                sqlSingelton.insertLPintoSQLTABLE(Log.getInstance().getlp(),res.getCan());

                //if true, the costumer can enter
                if(res.getCan()){
                    Log.WriteToLogFile(Log.getInstance().getlp(),"car license plate is valid, opening door...");
                    //open door to parking lot
                }else{//nothing happaning, but we still need to write to the log.
                    Log.WriteToLogFile(Log.getInstance().getlp(),res.getResponse());
                }
            }catch (SomethingWrongExcepction e){
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
