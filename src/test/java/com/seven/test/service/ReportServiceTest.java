package com.seven.test.service;

import com.seven.test.model.Report;
import com.seven.test.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static testdata.CompanyTestData.COMPANY1_ID;
import static testdata.CompanyTestData.COMPANY3_ID;
import static testdata.ReportTestData.*;

public class ReportServiceTest extends AbstractServiceTest {
    @Autowired
    private ReportService service;

    @Test
    public void testSave() {
        Report newReport = new Report(null, "NewReport", of(2017, Month.JULY, 01, 12, 30), "Content of NewReport");
        //newReport.setCompany(COMPANY1);

        Report created = service.save(newReport, COMPANY1_ID);
        newReport.setId(created.getId());
        List<Report> expected = Arrays.asList(REPORT1, newReport, REPORT2, REPORT4, REPORT3);
        List<Report> actual = service.getAll();
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() {
        // try to update report of wrong company
        service.update(REPORT1, COMPANY1_ID);
    }

    @Test
    public void testUpdate() {
        Report updated = REPORT1;
        updated.setName("updated");
        service.update(REPORT1, COMPANY3_ID);
        MATCHER.assertEquals(updated, service.get(REPORT1_ID, COMPANY3_ID));
    }

    @Test
    public void testDelete() {
        service.delete(REPORT1_ID, COMPANY3_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(REPORT2, REPORT4, REPORT3), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, 1);
    }

    @Test
    public void testGetWithCompany() {
        MATCHER.assertEquals(service.getWithCompany(REPORT1_ID, COMPANY3_ID), REPORT1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() {
        MATCHER.assertEquals(service.get(REPORT1_ID, COMPANY1_ID), REPORT1);
    }

    @Test
    public void testGet() {
        MATCHER.assertEquals(service.get(REPORT1_ID, COMPANY3_ID), REPORT1);
    }

    @Test
    public void testGetAll() {
        MATCHER.assertCollectionEquals(Arrays.asList(REPORT1, REPORT2, REPORT4, REPORT3), service.getAll());
    }

    @Test
    public void testGetAllByCompany() {
        MATCHER.assertCollectionEquals(Arrays.asList(REPORT1, REPORT4, REPORT3), service.getAllByCompany(COMPANY3_ID));
    }

    @Test
    public void testValidation() throws Exception {
        // empty name
        validateRootCause(() -> service.save(new Report(null, " ", of(2017, Month.JULY, 01, 12, 30), "Content of report 2 for 'Apollo'"), COMPANY1_ID), ConstraintViolationException.class);
        // empty date
        validateRootCause(() -> service.save(new Report(null, "Report 2 for 'Apollo'", null, "Content of report 2 for 'Apollo'"), COMPANY1_ID), ConstraintViolationException.class);
        // empty content
        validateRootCause(() -> service.save(new Report(null, "Report 2 for 'Apollo'", of(2017, Month.JULY, 01, 12, 30), ""), COMPANY1_ID), ConstraintViolationException.class);
    }
}