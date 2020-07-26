package testdata;

import com.seven.test.matcher.ModelMatcher;
import com.seven.test.model.Report;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;

public class ReportTestData {
    public static final ModelMatcher<Report> MATCHER = ModelMatcher.of(Report.class);

    public static final int REPORT1_ID = 300_000;

    public static final Report REPORT1 = new Report(REPORT1_ID, "Report 2 for 'Apollo'", of(2017, Month.JULY, 01, 15, 30), "Content of report 2 for 'Apollo'");
    public static final Report REPORT2 = new Report(REPORT1_ID + 1, "Report for 'Roga i kopita'", of(2017, Month.JUNE, 01, 15, 30), "Content of report for 'Roga i kopita'");
    public static final Report REPORT3 = new Report(REPORT1_ID + 2, "Report 1 for 'Apollo'", of(2017, Month.JANUARY, 01, 14, 30), "Content of report 1 for 'Apollo'");
    public static final Report REPORT4 = new Report(REPORT1_ID + 3, "Report 3 for Apollo", of(2017, Month.FEBRUARY, 02, 14, 00), "Content of report 3 for Apollo");

    public static final List<Report> REPORTS = Arrays.asList(REPORT1, REPORT2, REPORT3, REPORT4);
}
