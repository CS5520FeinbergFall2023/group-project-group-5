import java.util.ArrayList;
import java.util.List;

public class MyImage implements Image{
  private List<Operation> operationList;
  private Pixel[][] pixels;

  public void takeOperation(Operation operation){
    operationList.add(operation);
  }

  public void performOperations(){
    for (Operation operation : operationList) {
      operation.perform();
    }
    operationList.clear();
  }


}
