package com.seven.test.service;

import com.seven.test.model.Company;
import com.seven.test.model.User;
import com.seven.test.repository.CompanyRepository;
import com.seven.test.util.UserUtil;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seven.test.util.ValidationUtil.*;

@Service("companyService")
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final UserService userService;
    private final EmailService emailService;

    @Override
    @Transactional
    public Company save(@NonNull Company company) {
        log.info("save: " + company);
        checkNew(company);
        Company c = repository.save(company);

        // create new company owner
        User newOwner = UserUtil.createNewOwner(c);

        // there password is not encoded yet
        String msg = String.format(
                "Your login: %s%nYour password: %s%nYour company: %s",
                newOwner.getEmail(), newOwner.getPassword(), company.getName());

        userService.save(newOwner);

        // send email to just created owner
        emailService.sendSimpleMessage(newOwner.getEmail(), msg);

        return c;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        log.info("delete id = " + id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Company get(int id) throws NotFoundException {
        log.info("get id = " + id);
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public List<Company> getAll() {
        log.info("get all");
        return repository.findAll();
    }

    @Override
    public void update(@NonNull Company company, int id) {
        log.info("update: " + company);
        checkIdConsistent(company, id);
        repository.save(company);
    }
}
