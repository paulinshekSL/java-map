package test;

import main.InvalidKeyException;
import main.KeyNotFoundException;
import main.Map;
import main.MapWithList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MapWithListTest {

  @Test
  public void emptyMapShouldReturnEmptyKeySet() {
    Map<String, String> emptyMap  = new MapWithList<>();

    assertTrue("Empty map does not return empty key set", emptyMap.getAllKeys().isEmpty());
  }

  @Test
  public void addElementShouldShowUpInKeySet() {
    Map<String, String> testMap  = new MapWithList<>();
    testMap.add("Key", "Value");

    assertTrue("Key does not show up in getAllKeys when added using add method", testMap.getAllKeys().contains("Key"));
  }

  @Test
  public void containsKeyTrueWhenPutElement() {
    Map<String, String> testMap  = new MapWithList<>();
    testMap.add("Key", "Value");

    assertTrue("containsKey returns false when test for key that has been added using add method", testMap.containsKey("Key"));
  }

  @Test
  public void putElementTheyShouldBeAbleToGet() {
    String TEST_KEY = "Test key";
    String TEST_VALUE = "Value";

    Map<String, String> testMap  = new MapWithList<>();
    testMap.add(TEST_KEY, TEST_VALUE);

    // when
    String actualValue = testMap.get(TEST_KEY);

    assertEquals(actualValue, TEST_VALUE);
  }

  @Test
  public void putSameKeyTwiceShouldError() {
    try {
      Map<String, String> testMap  = new MapWithList<>();
      testMap.add("key", "value 1");
      testMap.add("key", "value 2");

      fail();
    } catch (InvalidKeyException e) {
      // test passes, do nothing
    }
  }

  @Test
  public void deleteNonExistentKeyShouldError() {
    try {
      Map<String, String> testMap  = new MapWithList<>();
      testMap.add("key", "value 1");

      testMap.delete("different key");

      fail();
    } catch (KeyNotFoundException e) {
      // test passes, do nothing
    }
  }

  @Test
  public void deleteKeyShouldRemoveFromKeySet() {
    String KEY_1 = "key 1";
    String KEY_2 = "key 2";

    Map<String, String> testMap  = new MapWithList<>();
    testMap.add(KEY_1, "value 1");
    testMap.add(KEY_2, "value 2");

    testMap.delete(KEY_2);

    assertEquals(1, testMap.getAllKeys().size());
    assertTrue("key still returned by getAllKeys after using delete method", !testMap.getAllKeys().contains(KEY_2));
  }

  @Test
  public void deleteKeyThenContainsKeyIsFalse() {
    String KEY_1 = "key 1";
    String KEY_2 = "key 2";

    Map<String, String> testMap  = new MapWithList<>();
    testMap.add(KEY_1, "value 1");
    testMap.add(KEY_2, "value 2");

    testMap.delete(KEY_2);

    assertFalse("containsKey should return false after deleting that key", testMap.containsKey(KEY_2));
  }

  @Test
  public void cannotAddNullKey() {
    try {
      Map<String, String> testMap  = new MapWithList<>();
      testMap.add(null, "value");

      fail();
    } catch (NullPointerException e) {
      // do nothing so test passes
    }
  }

  @Test
  public void cannotRemoveNull() {
    try {
      Map<String, String> testMap  = new MapWithList<>();
      testMap.delete(null);

      fail();
    } catch (NullPointerException e) {
      // do nothing so test passes
    }
  }

  @Test
  public void getNullKeyThrowsNullPointer() {
    try {
      Map<String, String> testMap  = new MapWithList<>();
      testMap.get(null);

      fail();
    } catch (NullPointerException e) {
      // do nothing so test passes
    }
  }

  @Test
  public void containsKeyNullThrowsNullPointer() {
    try {
      Map<String, String> testMap  = new MapWithList<>();
      testMap.containsKey(null);

      fail();
    } catch (Exception e) {
      // do nothing so test passes
    }
  }
}
