package com.nn.ishop.client.gui.barcode;
public abstract class AbstractBarcodeStrategy implements BarcodeStrategy {
  protected abstract CharacterCode[] getCodes();

  protected abstract String augmentWithChecksum(String text) throws BarcodeException;
  protected abstract String preprocess(String text) throws BarcodeException;

  protected abstract boolean isInterleaved();

  protected abstract char getStartSentinel();
  protected abstract char getStopSentinel();
  protected abstract byte getMarginWidth();
  protected abstract String getBarcodeLabelText(String text);
  protected abstract String postprocess(String text);
   

  public EncodedBarcode encode(String textToEncode, 
		  boolean checked) throws BarcodeException {

    String text;

    text = preprocess(textToEncode);
    text = checked ? augmentWithChecksum(text) : text;
    text = postprocess(text);

    // Simple to check to ensure start and end characters are not present in the
    // text to be encoded.
    if (((getStartSentinel() != 0xffff && text.indexOf(getStartSentinel()) >= 0)) ||
        (text.indexOf(getStopSentinel())) >= 0) {
      throw new BarcodeException("Invalid character in barcode");
    }

    if (getStartSentinel() != 0xffff) {
      text = getStartSentinel() + text;
    }

    text += getStopSentinel();

    int size = computeSize(text);
    BarcodeElement[] elements = new BarcodeElement[size];

    // Margin
    elements[0] = new BarcodeElement();
    elements[0].bar = false;
    elements[0].width = getMarginWidth();

    int len = text.length();
    int j = 1;
    for(int i = 0; i < len; i++) {
      char ch = text.charAt(i);
      CharacterCode cc = getCharacterCode(ch);
      if (cc == null) {
        throw new BarcodeException("Invalid character in barcode");
      }
      for (int k = 0; k < cc.widths.length; k++) {
        elements[j] = new BarcodeElement();
        elements[j].width = cc.widths[k];
        elements[j].bar = ((j % 2) == 0) ? false : true;
        if (isInterleaved() && ch != getStartSentinel() && ch != getStopSentinel()) {
          j += 2;
        } else {
          j++;
        }
      }
      if (isInterleaved() && ch != getStartSentinel() && ch != getStopSentinel()) {
        if (i % 2 == 1) {
          j -= (cc.widths.length * 2 - 1);
        } else {
          j -= 1;
        }
      }
    }

    elements[j] = new BarcodeElement();
    elements[j].bar = false;
    elements[j].width = getMarginWidth();
    j++;

    if (j != size) {
      throw new BarcodeException("Unexpected barcode size");
    }

    return new EncodedBarcode(elements, 
    		getBarcodeLabelText(textToEncode));
  }

  /**
   * Computes the length of the barcode (in bar/space modules) based on the
   * text to encode.
   *
   * @param text The text to encode including any check digit,
   * start and end sentinels.
   *
   * @return The number of module segments in this barcode (including margins).
   *
   * @throws BarcodeException Typically
   * occurs if attempting to encode invalid characters.
   */
  protected int computeSize(String text) throws BarcodeException {
    int size = 0;
    int l = text.length();
    for(int i = 0; i < l; i++) {
      char ch = text.charAt(i);
      CharacterCode cc = getCharacterCode(ch);
      if (cc == null) {
        throw new BarcodeException("Invalid character in barcode");
      }
      size = size + cc.widths.length;
    }
    size += 2;  // Margins
    return size;
  }

  /**
   * Looks for the specified character to encode in the CharacterCode array
   * returned by the {@link AbstractBarcodeStrategy#getCodes} method.
   *
   * @param character The character to encode.
   *
   * @return CharacterCode The element in the CharacterCode array (returned by
   * getCodes) that corresponds to the character passed to the method.
   */
  protected CharacterCode getCharacterCode(char character) {
    CharacterCode[] codes = getCodes();
    for (int i = 0; i < codes.length; i ++) {
      if (codes[i].character == character) {
        return codes[i];
      }
    }
    return null;
  }

  /**
   * Looks for an entry in the CharacterCode array
   * returned by the {@link AbstractBarcodeStrategy#getCodes} method,
   * by its <tt>check</tt> attribute.
   *
   * @param check The check attribute of the character being sought.
   *
   * @return CharacterCode The element in the CharacterCode array (returned by
   * getCodes) that corresponds to the character whose check attribute was passed
   * to the method.
   */
  protected CharacterCode getCharacterCode(int check) {
    CharacterCode[] codes = getCodes();
    for (int i = 0; i < codes.length; i ++) {
      if (codes[i].check == check) {
        return codes[i];
      }
    }
    return null;
  }

  /**
   * Inner class representing a character and its barcode encoding.
   */
  public static class CharacterCode {

    /** The character that is encoded */
    public char character;
    /** The widths of the modules (bars and spaces) of this encoded character */
    public byte[] widths;
    /** The check digit corresponding to this character, used in checksum calculations */
    public int check;

    /** Constructor which fully initializes the properties of the object. */
    public CharacterCode(char character, byte[] widths, int check) {
      this.character = character;
      this.widths = widths;
      this.check = check;
    }
  }
}



