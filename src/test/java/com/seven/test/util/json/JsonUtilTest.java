package com.seven.test.util.json;

import com.seven.test.model.Report;
import org.junit.Test;

import java.util.List;

import static testdata.ReportTestData.*;

public class JsonUtilTest {
    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(REPORT1);
        System.out.println(json);
        Report report = JsonUtil.readValue(json, Report.class);
        MATCHER.assertEquals(REPORT1, report);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(REPORTS);
        System.out.println(json);
        List<Report> reports = JsonUtil.readValues(json, Report.class);
        MATCHER.assertCollectionEquals(REPORTS, reports);
    }
}