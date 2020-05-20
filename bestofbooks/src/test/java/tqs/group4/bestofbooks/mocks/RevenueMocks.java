package tqs.group4.bestofbooks.mocks;


import tqs.group4.bestofbooks.model.Revenue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RevenueMocks {
    public final static Revenue revenue1 = new Revenue(150, BookOrderMocks.bookOrder1, "Publisher 1");
    public final static Revenue revenue2 = new Revenue(300, BookOrderMocks.bookOrder2, "Publisher 1");
    public final static List<Revenue> revenues = new ArrayList<>(Arrays.asList(revenue1, revenue2));
}
