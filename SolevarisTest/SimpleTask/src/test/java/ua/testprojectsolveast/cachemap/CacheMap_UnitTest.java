package ua.testprojectsolveast.cachemap;

import org.junit.Before;
import org.junit.Test;
import ua.testprojectsolveast.cachemap.base.CacheMap;
import static org.junit.Assert.*;

/**
 * JUnit test case for a ua.testprojectsolveast.cachemap.base.CacheMap implementation.
 * <p/>
 * Feel free to add more methods.
 */
public class CacheMap_UnitTest {
    CacheMap<Integer, String> cache = new ArrayCacheMap<Integer, String>() {

        @Override
        protected Long getCurrentTime() {
            return Clock.getTime();
        }

    };

    final static long TIME_TO_LIVE = 1000;

    @Before
    public void setUp() throws Exception {
        Clock.setTime(1000);

        //TODO instantiate cache object

        cache.setTimeToLive(TIME_TO_LIVE);
    }

    @Test
    public void testMoreItemsEquals() {
        cache.put(1, "apple1");
        cache.put(2, "apple2");
        cache.put(3, "apple3");
        cache.put(4, null);
        cache.put(5, "apple5");
        cache.put(6, "apple6");
        cache.put(7, "apple7");
        cache.put(8, "apple8");
        cache.put(9, "apple9");
        cache.put(10, "apple10");
        cache.put(11, "apple11");
        try {
            cache.put(null, "hello");
        } catch (IllegalArgumentException e) {
            assertEquals("Key must be not null!", e.getMessage());
        }

        assertTrue(cache.containsKey(4));
        assertTrue(cache.containsValue(null));
        assertNull(cache.get(4));
        assertEquals(11, cache.size());
        assertNull(cache.remove(12));
        assertEquals(11, cache.size());
        assertNotNull(cache.remove(5));
        assertEquals(10, cache.size());
        assertNull(cache.remove(4));
        assertEquals(9, cache.size());

        CacheMap<Integer, String> tmp = new ArrayCacheMap<Integer, String>() {

            @Override
            protected Long getCurrentTime() {
                return Clock.getTime();
            }

        };

        tmp.put(1, "apple1");
        tmp.put(2, "apple2");
        tmp.put(3, "apple3");
        tmp.put(6, "apple6");
        tmp.put(7, "apple7");
        tmp.put(8, "apple8");
        tmp.put(9, "apple9");
        tmp.put(10, "apple10");
        tmp.put(11, "apple11");
        tmp.setTimeToLive(TIME_TO_LIVE);

        assertEquals(tmp, cache);
        cache.clear();
        assertEquals(0, cache.size());
    }

    @Test
    public void testExpiry() throws Exception {
        cache.put(1, "apple");
        assertEquals("apple", cache.get(1));
        assertFalse(cache.isEmpty());
        Clock.setTime(3000);
        assertNull(cache.get(1));
        assertTrue(cache.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(0, cache.size());
        cache.put(1, "apple");
        assertEquals(1, cache.size());
        Clock.setTime(3000);
        assertEquals(0, cache.size());
    }

    @Test
    public void testPartialExpiry() throws Exception {
        //Add an apple, it will expire at 2000
        cache.put(1, "apple");
        Clock.setTime(1500);
        //Add an orange, it will expire at 2500
        cache.put(2, "orange");

        assertEquals("apple", cache.get(1));
        assertEquals("orange", cache.get(2));
        assertEquals(2, cache.size());

        //Set time to 2300 and check that only the apple has disappeared
        Clock.setTime(2300);

        assertNull(cache.get(1));
        assertEquals("orange", cache.get(2));
        assertEquals(1, cache.size());
    }

    @Test
    public void testPutReturnValue() {
        cache.put(1, "apple");
        assertNotNull(cache.put(1, "banana"));
        Clock.setTime(3000);
        assertNull(cache.put(1, "mango"));
    }

    @Test
    public void testRemove() throws Exception {
        assertNull(cache.remove(new Integer(1)));

        cache.put(new Integer(1), "apple");

        assertEquals("apple", cache.remove(new Integer(1)));
        assertNull(cache.get(new Integer(1)));
        assertEquals(0, cache.size());
    }

    @Test
    public void testContainsKeyAndContainsValue() {
        assertFalse(cache.containsKey(1));
        assertFalse(cache.containsValue("apple"));
        assertFalse(cache.containsKey(2));
        assertFalse(cache.containsValue("orange"));

        cache.put(1, "apple");
        assertTrue(cache.containsKey(1));
        assertTrue(cache.containsValue("apple"));
        assertFalse(cache.containsKey(2));
        assertFalse(cache.containsValue("orange"));

        Clock.setTime(3000);
        assertFalse(cache.containsKey(1));
        assertFalse(cache.containsValue("apple"));
        assertFalse(cache.containsKey(2));
        assertFalse(cache.containsValue("orange"));

    }

}
