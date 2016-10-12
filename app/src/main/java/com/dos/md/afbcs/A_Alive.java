public class A_Alive extends Acitvity{
  @Override protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    Window w= getWindow();
    w.setGravity(Gravity.RIGHT| Gravity.TOP);
    WindowManager.LayoutParams lp = w.getAttributes();
    lp.x = 0;
    lp.y = 0;
    lp.width = 1;
    lp.height = 1;
    w.setAttributes(lp);  
  }
}
