/*依据不同的type返回不同的data值  eg:  {"list":[{
    "type":0,
    "data":{
        "id":1,
        "color":"red"
    }
},{
    "type":1,
    "data":{
        "id":1,
        "name":"case"
    }
}]}*/


public class TypeSuper {

}

class TypeA extends TypeSuper {

public int id;

public String name;

 

public TypeA(int id, String name) {

super();

this.id = id;

this.name = name;

}

}

class TypeB extends TypeSuper { 

　　public int id;

　　public String color;

 
　　public TypeB(int id, String color) {

　　　　super();

　　　　this.id = id;

　　　　this.color = color;

　　}

}

  class TypeResult {

List<TypeSuper> data = new ArrayList<TypeSuper>();

}

public class TypeResultDeserializer implements JsonDeserializer<TypeResult> {

    @Override
    public TypeResult deserialize(JsonElement arg0, Type arg1,
            JsonDeserializationContext arg2) throws JsonParseException {
        JsonObject obj = arg0.getAsJsonObject();
        JsonArray asJsonArray = obj.get("list").getAsJsonArray();
        TypeResult result = new TypeResult();
        for (JsonElement jsonElement : asJsonArray) {
            JsonObject jsonOb = jsonElement.getAsJsonObject();
            int type = jsonOb.get("type").getAsInt();
            if (type == 0) {
                JsonObject child = jsonOb.get("data").getAsJsonObject();
                int id = child.get("id").getAsInt();
                String name = child.get("color").getAsString();
                result.data.add(new TypeB(id, name));
            } else if(type == 1) {
                JsonObject child = jsonOb.get("data").getAsJsonObject();
                int id = child.get("id").getAsInt();
                String name = child.get("color").getAsString();
                result.data.add(new TypeA(id, name));
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        GsonBuilder gsonb = new GsonBuilder();
        gsonb.registerTypeAdapter(TypeResult.class, new TypeResultDeserializer());
        gsonb.serializeNulls();
        Gson gson = gsonb.create();
        String json = "{\"list\":[{" + "\"type\":0," + "\"data\":{"
                + "\"id\":1," + "\"color\":\"red\"" + "}" + "},{"
                + "\"type\":1," + "\"data\":{" + "\"id\":1,"
                + "\"color\":\"case\"" + "}" + "}]}";
        List<TypeSuper> item = gson.fromJson(json, TypeResult.class).data;
        for (TypeSuper baseItem : item) {
            if (baseItem instanceof TypeA) {
                System.out.println(((TypeA) baseItem).name);
            } else if (baseItem instanceof TypeB) {
                System.out.println(((TypeB) baseItem).color);
            }
        }
    }
}
