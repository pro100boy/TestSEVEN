package testdata;

import com.seven.test.matcher.ModelMatcher;
import com.seven.test.model.Company;

import java.util.Arrays;
import java.util.List;

public class CompanyTestData {
    public static final ModelMatcher<Company> MATCHER = ModelMatcher.of(Company.class);

    public static final int COMPANY1_ID = 100_000;
    public static final int COMPANY2_ID = 100_001;
    public static final int COMPANY3_ID = 100_002;

    public static final Company COMPANY1 = new Company(COMPANY1_ID, "ATB company", "atb.cmp@test.com", "address of ATB company");
    public static final Company COMPANY2 = new Company(COMPANY2_ID, "Roga i kopita", "rik@test.com", "address of 'Roga i kopita' company");
    public static final Company COMPANY3 = new Company(COMPANY3_ID, "Apollo", "apollo@test.com", "address of 'Apollo' company");

    public static final List<Company> COMPANIES = Arrays.asList(COMPANY1, COMPANY2, COMPANY3);
}
