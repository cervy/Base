public class BR_Alive extends BroadcastReceiver{
  @Override public onReceive(Context context, Intent intent){
    String a= intent.getAction();
    if(a.equals(Intent.ACTION_SCREEN_OFF)){
      //startA_Alvie
    } else if(a.equals(Intent.ACTION_USER_PRESENT)){
      //finish A_Alive
    }
  }
}
