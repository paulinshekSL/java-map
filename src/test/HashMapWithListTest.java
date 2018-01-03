package test;

import main.DuplicateKeyException;
import main.HashMapWithList;
import main.Map;
import main.NonExistentKeyException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HashMapWithListTest {

  @Test
  public void emptyMapShouldReturnEmptyKeySet() {
    Map<String, String> emptyMap  = new HashMapWithList<>();

    assertTrue("Empty map does not return empty key set", emptyMap.getAllKeys().isEmpty());
  }

  @Test
  public void addElementShouldShowUpInKeySet() {
    Map<String, String> testMap  = new HashMapWithList<>();
    testMap.add("Key", "Value");

    assertTrue("Key does not show up in getAllKeys when added using add method", testMap.getAllKeys().contains("Key"));
  }

  @Test
  public void containsKeyTrueWhenPutElement() {
    Map<String, String> testMap  = new HashMapWithList<>();
    testMap.add("Key", "Value");

    assertTrue("containsKey returns false when test for key that has been added using add method", testMap.containsKey("Key"));
  }

  @Test
  public void putElementTheyShouldBeAbleToGet() {
    String TEST_KEY = "Test key";
    String TEST_VALUE = "Value";

    Map<String, String> testMap  = new HashMapWithList<>();
    testMap.add(TEST_KEY, TEST_VALUE);

    // when
    String actualValue = testMap.get(TEST_KEY);

    assertEquals(actualValue, TEST_VALUE);
  }

  @Test
  public void putSameKeyTwiceShouldError() {
    try {
      Map<String, String> testMap  = new HashMapWithList<>();
      testMap.add("key", "value 1");
      testMap.add("key", "value 2");

      fail();
    } catch (DuplicateKeyException e) {
      // test passes, do nothing
    }
  }

  @Test
  public void deleteNonExistentKeyShouldError() {
    try {
      Map<String, String> testMap  = new HashMapWithList<>();
      testMap.add("key", "value 1");

      testMap.delete("different key");

      fail();
    } catch (NonExistentKeyException e) {
      // test passes, do nothing
    }
  }

  @Test
  public void deleteKeyShouldRemoveFromKeySet() {
    String KEY_1 = "key 1";
    String KEY_2 = "key 2";

    Map<String, String> testMap  = new HashMapWithList<>();
    testMap.add(KEY_1, "value 1");
    testMap.add(KEY_2, "value 2");

    testMap.delete(KEY_2);

    assertEquals(1, testMap.getAllKeys().size());
    assertTrue("key still returned by getAllKeys after using delete method", testMap.getAllKeys().contains(KEY_2));
  }

  @Test
  public void deleteKeyThenContainsKeyIsFalse() {
    String KEY_1 = "key 1";
    String KEY_2 = "key 2";

    Map<String, String> testMap  = new HashMapWithList<>();
    testMap.add(KEY_1, "value 1");
    testMap.add(KEY_2, "value 2");

    testMap.delete(KEY_2);

    assertFalse("containsKey should return false after deleting that key", testMap.containsKey(KEY_2));
  }
}
