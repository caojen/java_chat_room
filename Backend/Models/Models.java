package Backend.Models;

import Backend.Models.ModelType.ModelTypes;

public interface Models {
  /**
   * get the Models' type(in ModelType)
   * @return a type string
   */
  public String getTypeStr();

  /**
   * get the Models' type(in ModelType)
   * @return a ModelTypes
   */
  public ModelTypes getType();
}
