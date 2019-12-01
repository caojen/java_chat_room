package Frontend.Console;

/**
 * A interface for dealing text(send or receive)
 */
public interface Consoler  {
  /**
   * The Test showing in the consoler
   * The message should be ordered, use LinkHashMap
   * Messages will present in key-value map
   */
  // public Map<String, String> showText = new LinkedHashMap<String, String>();

  /**
   * append a String to this console showing text
   * @param data The data that will append into the showText
   * @param key The key of the data
   * @throws Excepotion if key already exists.
   */
  public void append(String key, String data) throws Exception;

  /**
   * To delete a key-value in showText
   * @param key
   * @throws Exception if key not exists.
   */
  public void delete(String key) throws Exception;

  /**
   * The main function
   * This will call a new Thread
   */
  public void run();

  /**
   * get the console text' with String
   */
  public String get();
}