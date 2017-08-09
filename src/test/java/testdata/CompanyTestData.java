package testdata;

import com.seven.test.matcher.ModelMatcher;
import com.seven.test.model.Company;

import java.util.Arrays;
import java.util.List;

public class CompanyTestData {
    public static final ModelMatcher<Company> MATCHER = ModelMatcher.of(Company.class);

    public static final int COMPANY1_ID = 100_000;

    public static final Company COMPANY1 = new Company(COMPANY1_ID, "ATB company", "atb.cmp@test.com", "address of ATB company");
    public static final Company COMPANY2 = new Company(COMPANY1_ID + 1, "Рога и копыта", "rik@test.com", "address of 'Рога и копыта' company");
    public static final Company COMPANY3 = new Company(COMPANY1_ID + 2, "Apollo", "apollo@test.com", "address of 'Apollo' company");

    public static final List<Company> COMPANIES = Arrays.asList(COMPANY1, COMPANY2, COMPANY3);
}
