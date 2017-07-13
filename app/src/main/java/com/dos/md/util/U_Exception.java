 /*new InputStreamProcessingTemplate(){
        public void doProcess(InputStream input) throws IOException{
            int inChar = input.read();
            while(inChar !- -1){
                //do something with the chars...
            }
        }
    }.process("someFile.txt");*/

public abstract class InputStreamProcessingTemplate {

    public void process(String fileName) {
        IOException processException = null;
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);

            doProcess(input);
        } catch (IOException e) {//todo Exception
            processException = e;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    if (processException != null) {
                        throw new MyException(processException, e,
                                "Error message..." +
                                        fileName);
                    } else {
                        throw new MyException(e,
                                "Error closing InputStream for file " +
                                        fileName;
                    }
                }
            }
            if (processException != null) {
                throw new MyException(processException,
                        "Error processing InputStream for file " +
                                fileName;
            }
        }

        //override this method in a subclass, to process the stream.

    public abstract void doProcess(InputStream input) throws IOException;

   
}
