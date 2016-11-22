class U_db extends SQLiteOpenHelper {  
    private static final String DATABASE_NAME="lunchlist.db";
    private static final int SCHEMA_VERSION=2;//升级之后的
      
    public U_db(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);  
    }  
      
    @Override  
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sth (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, notes TEXT, phone TEXT);");  
    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        if (oldVersion==1 && newVersion==2) {  
            db.execSQL("ALTER TABLE sth ADD phone TEXT;");  
        }  
    }  
  
    public Cursor getAll(String where, String orderBy) {where是调用时候传进来的搜索内容,orderby是设置中传进来的列表排序类型  
        StringBuilder buf=new StringBuilder("SELECT _id, name, address, type, notes, phone FROM sth");  
          
        if (where!=null) {  
            buf.append(" WHERE ");  
            buf.append(where);  
        }  
          
        if (orderBy!=null) {  
            buf.append(" ORDER BY ");  
            buf.append(orderBy);  
        }  
          
        return(getReadableDatabase().rawQuery(buf.toString(), null));  
    }  
      
    public Cursor getById(String id) {//根据点击事件获取id
        String[] args={id};  
  
        return(getReadableDatabase().rawQuery("SELECT _id, name, address, type, notes, phone FROM sth WHERE _ID=?", args));  
    }  
      
    public void insert(String name, String address, String type, String notes, String phone) {  
        ContentValues cv=new ContentValues();  
                      
        cv.put("name", name);  
        cv.put("address", address);  
        cv.put("type", type);  
        cv.put("notes", notes);  
        cv.put("phone", phone);  
          
        getWritableDatabase().insert("sth", "name", cv);  
    }  
      
    public void update(String id, String name, String address, String type, String notes, String phone) {  
        ContentValues cv=new ContentValues();  
        String[] args={id};  
          
        cv.put("name", name);  
        cv.put("address", address);  
        cv.put("type", type);  
        cv.put("notes", notes);  
        cv.put("phone", phone);  
          
        getWritableDatabase().update("restaurants", cv, "_ID=?", args);  
    }  
      
    public String getName(Cursor c) {  
        return(c.getString(1));  
    }  
      
    public String getAddress(Cursor c) {  
        return(c.getString(2));  
    }  
      
    public String getType(Cursor c) {  
        return(c.getString(3));  
    }  
      
    public String getNotes(Cursor c) {  
        return(c.getString(4));  
    }  
      
    public String getPhone(Cursor c) {  
        return(c.getString(5));  
    }  
} 
