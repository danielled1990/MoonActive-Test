import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MoonActiveParkingLotMain {
    public static void main(String[] args) {
        while(true){
            try{

                System.out.println(String.format("Welcome to Moon active parking lot!"));

                InputStreamReader streamReader = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(streamReader);

                Log.getInstance().WriteToLogFile("Waiting for a car to show");

                String urlOfImage = bufferedReader.readLine().trim();

                Log.getInstance().WriteToLogFile("New car arrived");

                System.out.println("please wait while we procces your plate number....");

                IfCarCanEnterOrNot res = IfCarCanEnterOrNot.checkIfVihicleCanEnterByGivenCode
                        (new CheckIfLicensePlateIsValid().CheckLicenePlate
                                (new GetTextFromImage().getTextFromImageAPI(urlOfImage)));
                System.out.println(Log.getInstance().getlp()+" " +res.getResponse());

                //ENTER TO DB
                SQLHelper sqlSingelton = new SQLHelper();
                sqlSingelton.init();
                sqlSingelton.insertLPintoSQLTABLE(Log.getInstance().getlp(),res.getCan());

                if(res.getCan()){
                    Log.WriteToLogFile(Log.getInstance().getlp(),"car license plate is valid, opening door...");
                    //open door to parking lot
                }else{
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
