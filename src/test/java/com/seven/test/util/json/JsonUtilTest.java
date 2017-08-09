package com.seven.test.util.json;

import com.seven.test.model.Company;
import org.junit.Test;

import java.util.List;

import static testdata.CompanyTestData.*;

public class JsonUtilTest {
    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(COMPANY1);
        System.out.println(json);
        Company company = JsonUtil.readValue(json, Company.class);
        MATCHER.assertEquals(COMPANY1, company);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(COMPANIES);
        System.out.println(json);
        List<Company> meals = JsonUtil.readValues(json, Company.class);
        MATCHER.assertCollectionEquals(COMPANIES, meals);
    }
}